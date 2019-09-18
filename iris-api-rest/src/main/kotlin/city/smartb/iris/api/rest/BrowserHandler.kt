package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.model.Message
import city.smartb.iris.api.rest.model.Response
import city.smartb.iris.api.rest.model.Session
import city.smartb.iris.api.rest.websocket.AbstractHandler
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class BrowserHandler (
        private val connectionFactory: ConnectionFactory,
        private val template: RabbitTemplate,
        private val objectMapper: ObjectMapper
) : AbstractHandler<Message, Response>() {

    private val logger = LoggerFactory.getLogger(BrowserHandler::class.java)

    override fun receiveFromDevice(session: Session, message: Message) {
        logger.info("Receive message from browser to phone[${message}]")
        val toPhoneQueue = session.getQueueToSendToPhone()
        template.convertAndSend(toPhoneQueue, message);
    }

    override fun sendToDevice(session: Session): Flux<Response> {
        val toBrowserQueue = session.getQueueToSendToBrowser()
        val mlc = SimpleMessageListenerContainer(connectionFactory);
        mlc.setQueueNames(toBrowserQueue)
        return Flux.create<Response> { emitter ->
            mlc.setupMessageListener { messageListener ->
                val payload = toResponse(messageListener.body)
                logger.info("Send message from phone to Brower[${payload}]")
                emitter.next(payload)
            }
            emitter.onRequest { v -> mlc.start() }
            emitter.onDispose { mlc.stop() }
        }
    }

    fun toResponse(json: ByteArray): Response {
        return objectMapper.readValue(json, Response::class.java)
    }

    override fun toValue(json: ByteArray): Message {
        return objectMapper.readValue(json, Message::class.java)
    }

}