package city.smartb.iris.api.rest.features.messages

import city.smartb.iris.api.rest.exception.InvalidMessageException
import city.smartb.iris.api.rest.model.jwt.Jwt
import city.smartb.iris.api.rest.model.*
import city.smartb.iris.api.rest.model.jwt.asByte64
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

    override fun receiveFromDevice(channelSession: ChannelSession, response: MessageResponse) {
        logger.info("Receive message from phone to browser[${response}]")
        when (response.action) {
            ActionType.PUB_KEY -> sendSignJwtToSigner(response, channelSession)
            ActionType.SIGN_PUB_KEY -> sendSendJwtToApplication(response, channelSession)
            else -> sendToBrowser(channelSession, response)
        }
    }

    private fun sendSendJwtToApplication(response: MessageResponse, channelSession: ChannelSession) {
        val signature = response.payload["signature"] ?:
            throw InvalidMessageException("Message Type[${response.action}] must contains signature in payload")

        val jwtKey = channelSession.getJWTKey()
        val jwtKeyHash = jwtKey!!.append(signature!!)
        logger.debug("Session[${channelSession.channelId}] JWT To Application: ${jwtKeyHash}")
        sendToBrowser(channelSession, AuthMessageResponse(jwtKeyHash))
    }

    private fun sendSignJwtToSigner(response: MessageResponse, channelSession: ChannelSession) {
        val publicKey = response.payload["publicKey"]
                ?: throw InvalidMessageException("Message Type ${ActionType.PUB_KEY} must contains publicKey in payload")

        logger.debug("Session[${channelSession.channelId}] Publuc key received from phone: ${publicKey}")
        val jwtToSign = Jwt.builder()
                .publicKey(publicKey)
                .fromNow()
                .valid(3, ChronoUnit.HOURS)
                .build()

        logger.debug("Session[${channelSession.channelId}] JWT to sign[${jwtToSign.asString()}]")
        channelSession.setJWTKey(jwtToSign)

        sendSignPubKeyMessageQuery(channelSession, jwtToSign)
    }

    private fun sendSignPubKeyMessageQuery(channelSession: ChannelSession, jwtToSign: Jwt) {
        val signPubKeyMessageQuery = SignPubKeyMessageQuery(
                sha256 = jwtToSign.asSHA256ForNoneWithRSA().asByte64()
        )
        logger.debug("Session[${channelSession.channelId}] SignPubKeyMessageQuery[${signPubKeyMessageQuery}]")

        sendToPhone(channelSession, signPubKeyMessageQuery)
    }

    override fun toValueReceivedFromDevice(body: ByteArray): MessageResponse = toMessageResponse(body)
    override fun toValueSendToDevice(body: ByteArray): MessageQuery = toMessageQuery(body)

    override fun getQueueNameToListen(channelSession: ChannelSession) = channelSession.getQueueToSendToSigner()


}