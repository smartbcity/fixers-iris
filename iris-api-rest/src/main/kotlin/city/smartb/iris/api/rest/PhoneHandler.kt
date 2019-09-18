package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.model.Message
import city.smartb.iris.api.rest.model.Response
import city.smartb.iris.api.rest.model.Session
import city.smartb.iris.api.rest.websocket.AbstractHandler
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class PhoneHandler(
        private val connectionFactory: ConnectionFactory,
        private val template: RabbitTemplate,
        private val objectMapper: ObjectMapper
) : AbstractHandler<Response, Message>() {

    private val logger = LoggerFactory.getLogger(BrowserHandler::class.java)

    override fun receiveFromDevice(session: Session, message: Response) {
        logger.info("Receive message from phone to browser[${message}]")
        val getQueueToSendToBrowser = session.getQueueToSendToBrowser()
        template.convertAndSend(getQueueToSendToBrowser, message);
    }

    override fun sendToDevice(session: Session): Flux<Message> {
        val toPhoneQueueCleaner = session.getQueueToSendToPhone()
        val mlc = SimpleMessageListenerContainer(connectionFactory);
        mlc.setQueueNames(toPhoneQueueCleaner)
        return Flux.create<Message> { emitter ->
            mlc.setupMessageListener { messageListener ->
                val payload = toMessage(messageListener.body)
                logger.info("Send message from Brower to phone[${payload}]")
                emitter.next(payload)
            }
            emitter.onRequest { v -> mlc.start() }
            emitter.onDispose { mlc.stop() }
        }
    }

    fun getMessagesFromBroker(session: Session): Flux<Response> {
        val mlc = SimpleMessageListenerContainer(connectionFactory);
        return Flux.create<Response> { emitter ->
            mlc.setupMessageListener { messageListener ->
                val payload = toValue(messageListener.body)
                emitter.next(payload)
            }
            emitter.onRequest { v -> mlc.start() }
            emitter.onDispose { mlc.stop() }
        }
    }

    fun toMessage(json: ByteArray): Message {
        return objectMapper.readValue(json)
    }

    override fun toValue(json: ByteArray): Response {
        return objectMapper.readValue(json)
    }

//    private val eventFlux = Flux.generate<String> { sink ->
//        val event = Message(
//                url = "FromIris",
//                application = "FromIris"
//        )
//        try {
//            sink.next(objectMapper.writeValueAsString(event))
//        } catch (e: JsonProcessingException) {
//            sink.error(e)
//        }
//    }
//
//    private val intervalFlux: Flux<Message> = Flux.interval(Duration.ofMillis(5000L))
//            .zipWith(eventFlux).map {
//                AuthMessage(
//                        url = "hhtt",
//                        application = "IRIS",
//                        action = "test"
//                )
//            }

}