package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.exception.InvalidMessageException
import city.smartb.iris.api.rest.jwt.Jwt
import city.smartb.iris.api.rest.model.*
import city.smartb.iris.api.rest.sign.asByte64
import city.smartb.iris.api.rest.websocket.AbstractHandler
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.time.temporal.ChronoUnit

@Service
class SignerHandler(
        connectionFactory: ConnectionFactory,
        template: RabbitTemplate,
        objectMapper: ObjectMapper
) : AbstractHandler<MessageResponse, MessageQuery>(connectionFactory, template, objectMapper) {

    override fun receiveFromDevice(session: Session, response: MessageResponse) {
        logger.info("Receive message from phone to browser[${response}]")
        when (response.action) {
            ActionType.PUB_KEY -> sendSignJwtToSigner(response, session)
            ActionType.SIGN_PUB_KEY -> sendSendJwtToApplication(response, session)
            else -> sendToBrowser(session, response)
        }
    }

    private fun sendSendJwtToApplication(response: MessageResponse, session: Session) {
        val signature = response.payload["signature"] ?:
            throw InvalidMessageException("Message Type[${response.action}] must contains signature in payload")

        val jwtKey = session.getJWTKey()
        val jwtKeyHash = jwtKey!!.append(signature!!)
        logger.debug("Session[${session.id}] JWT To Application: ${jwtKeyHash}")
        sendToBrowser(session, AuthMessageResponse(jwtKeyHash))
    }

    private fun sendSignJwtToSigner(response: MessageResponse, session: Session) {
        val publicKey = response.payload["publicKey"]
                ?: throw InvalidMessageException("Message Type ${ActionType.PUB_KEY} must contains publicKey in payload")

        logger.debug("Session[${session.id}] Publuc key received from phone: ${publicKey}")
        val jwtToSign = Jwt.builder()
                .publicKey(publicKey)
                .fromNow()
                .valid(3, ChronoUnit.HOURS)
                .build()

        logger.debug("Session[${session.id}] JWT to sign[${jwtToSign.asString()}]")
        session.setJWTKey(jwtToSign)

        sendSignPubKeyMessageQuery(session, jwtToSign)
    }

    private fun sendSignPubKeyMessageQuery(session: Session, jwtToSign: Jwt) {
        val signPubKeyMessageQuery = SignPubKeyMessageQuery(
                sha256 = jwtToSign.asSHA256ForNoneWithRSA().asByte64()
        )
        logger.debug("Session[${session.id}] SignPubKeyMessageQuery[${signPubKeyMessageQuery}]")

        sendToPhone(session, signPubKeyMessageQuery)
    }

    override fun toValueReceivedFromDevice(body: ByteArray): MessageResponse = toMessageResponse(body)
    override fun toValueSendToDevice(body: ByteArray): MessageQuery = toMessageQuery(body)

    override fun getQueueNameToListen(session: Session) = session.getQueueToSendToSigner()


}