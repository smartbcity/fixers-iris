package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.model.Message
import city.smartb.iris.api.rest.model.Response
import city.smartb.iris.api.rest.model.Session
import city.smartb.iris.api.rest.websocket.AbstractHandler
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class ApplicationHandler (
        connectionFactory: ConnectionFactory,
        template: RabbitTemplate,
        objectMapper: ObjectMapper
) : AbstractHandler<Message, Response>(connectionFactory, template, objectMapper) {

    override fun receiveFromDevice(session: Session, message: Message) {
        logger.info("Receive message from browser to phone[${message}]")
        sendToPhone(session, message)
    }

    override fun toValueSendToDevice(body: ByteArray): Response {
        return toResponse(body)
    }
    override fun toValueReceivedFromDevice(body: ByteArray): Message {
        return toMessage(body)
    }

    override fun getQueueNameToListen(session: Session): String {
       return session.getQueueToSendToApplication()
    }

}