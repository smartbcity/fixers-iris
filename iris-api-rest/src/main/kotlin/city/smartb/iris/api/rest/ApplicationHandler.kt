package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.model.*
import city.smartb.iris.api.rest.sign.asByte64
import city.smartb.iris.api.rest.websocket.AbstractHandler
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class ApplicationHandler(
        connectionFactory: ConnectionFactory,
        template: RabbitTemplate,
        objectMapper: ObjectMapper
) : AbstractHandler<MessageQuery, MessageResponse>(connectionFactory, template, objectMapper) {

    override fun receiveFromDevice(session: Session, message: MessageQuery) {
        logger.info("Receive message from browser to phone[${message}]")
        when (message.action) {
            ActionType.AUTH -> sendPubKeyToSigner(session, message)
            else -> sendToPhone(session, message)
        }
    }


    fun sendPubKeyToSigner(session: Session, message: MessageQuery) {
        val pubKeyMessageQuery = PubKeyMessageQuery()
        logger.info("Convert Message[${message.action}] to [${pubKeyMessageQuery.action}] ")
        sendToPhone(session, pubKeyMessageQuery)
    }


    override fun toValueSendToDevice(body: ByteArray): MessageResponse {
        return toMessageResponse(body)
    }

    override fun toValueReceivedFromDevice(body: ByteArray): MessageQuery {
        return toMessageQuery(body)
    }

    override fun getQueueNameToListen(session: Session): String {
        return session.getQueueToSendToApplication()
    }

}