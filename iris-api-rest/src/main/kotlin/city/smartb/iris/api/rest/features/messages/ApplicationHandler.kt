package city.smartb.iris.api.rest.features.messages

import city.smartb.iris.api.rest.model.*
import city.smartb.iris.api.rest.websocket.AbstractHandler
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class ApplicationHandler(
        val browserMessageTransformer: BrowserMessageTransformer,
        connectionFactory: ConnectionFactory,
        template: RabbitTemplate,
        objectMapper: ObjectMapper
) : AbstractHandler<MessageQuery, MessageResponse>(connectionFactory, template, objectMapper) {

    override fun receiveFromDevice(channelSession: ChannelSession, message: MessageQuery) {
        logger.info("Receive message from browser to phone[${message}]")
        val msgToSend = browserMessageTransformer.transform(channelSession, message)
        sendToPhone(channelSession, msgToSend)
    }

    fun sendPubKeyToSigner(channelSession: ChannelSession, message: MessageQuery) {
        val pubKeyMessageQuery = PubKeyMessageQuery()
        logger.info("Convert Message[${message.action}] to [${pubKeyMessageQuery.action}] ")
        sendToPhone(channelSession, pubKeyMessageQuery)
    }


    override fun toValueSendToDevice(body: ByteArray): MessageResponse {
        return toMessageResponse(body)
    }

    override fun toValueReceivedFromDevice(body: ByteArray): MessageQuery {
        return toMessageQuery(body)
    }

    override fun getQueueNameToListen(channelSession: ChannelSession): String {
        return channelSession.getQueueToSendToApplication()
    }

}