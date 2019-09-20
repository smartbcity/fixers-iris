package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.exception.InvalidResponseException
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
) : AbstractHandler<Response, Message>(connectionFactory, template, objectMapper) {

    override fun receiveFromDevice(session: Session, response: Response) {
        logger.info("Receive message from phone to browser[${response}]")
        when (response.action) {
            ActionType.AUTH_PUB_KEY -> sendSignJwtToSigner(response, session)
            ActionType.SIGN_PUB_KEY -> sendSendJwtToApplication(response, session)
            else -> sendToBrowser(session, response)
        }
    }

    private fun sendSendJwtToApplication(response: Response, session: Session) {
        val signature = response.payload.get("signature")
        session.getJWTKey()
    }

    private fun sendSignJwtToSigner(response: Response, session: Session) {
        val publicKey = response.payload.get("publicKey")
                ?: throw InvalidResponseException("Message Type ${ActionType.AUTH_PUB_KEY} must contains publicKey in payload")

        val jwtToSign = Jwt.builder()
                .publicKey(publicKey)
                .fromNow()
                .valid(3, ChronoUnit.HOURS)
                .build()

        session.setJWTKey(jwtToSign)
        sendToPhone(session, SignMessage(
                url = "",
                sha256 = jwtToSign.asSHA256ForNoneWithRSA().asByte64(),
                application = response.application
        ))
    }

    override fun toValueReceivedFromDevice(body: ByteArray): Response = toResponse(body)
    override fun toValueSendToDevice(body: ByteArray): Message = toMessage(body)

    override fun getQueueNameToListen(session: Session) = session.getQueueToSendToSigner()


}