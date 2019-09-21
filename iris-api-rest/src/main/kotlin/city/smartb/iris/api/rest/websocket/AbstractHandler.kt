package city.smartb.iris.api.rest.websocket

import city.smartb.iris.api.rest.model.Message
import city.smartb.iris.api.rest.model.MessageQuery
import city.smartb.iris.api.rest.model.MessageResponse
import city.smartb.iris.api.rest.model.Session
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
abstract class AbstractHandler<RECEIVE_FROM_DEVICE : Message, SEND_TO_DEVICE : Message>(
        private val connectionFactory: ConnectionFactory,
        private val template: RabbitTemplate,
        private val objectMapper: ObjectMapper
) {

    protected val logger = LoggerFactory.getLogger(this::class.java)

    abstract fun receiveFromDevice(session: Session, value: RECEIVE_FROM_DEVICE);
    abstract fun toValueReceivedFromDevice(it: ByteArray): RECEIVE_FROM_DEVICE

    protected abstract fun toValueSendToDevice(body: ByteArray): SEND_TO_DEVICE
    protected abstract fun getQueueNameToListen(session: Session): String

    fun transferToDevice(session: Session): Flux<SEND_TO_DEVICE> {
        val queueNameToListen = getQueueNameToListen(session)
        val mlc = SimpleMessageListenerContainer(connectionFactory);
        mlc.setQueueNames(queueNameToListen)
        return listenFlux(mlc)
    }

    private fun listenFlux(mlc: SimpleMessageListenerContainer): Flux<SEND_TO_DEVICE> {
        return Flux.create<SEND_TO_DEVICE> { emitter ->
            mlc.setupMessageListener { messageListener ->
                val payload = toValueSendToDevice(messageListener.body)
                logger.info("Send message from Brower to phone[${payload}]")
                emitter.next(payload)
            }
            emitter.onRequest { v -> mlc.start() }
            emitter.onDispose { mlc.stop() }
        }
    }

    protected fun sendToBrowser(session: Session, response: Message) {
        val getQueueToSendToBrowser = session.getQueueToSendToApplication()
        template.convertAndSend(getQueueToSendToBrowser, response);
    }

    protected fun sendToPhone(session: Session, message: Message) {
        val getQueueToSendToSigner = session.getQueueToSendToSigner()
        template.convertAndSend(getQueueToSendToSigner, message)
    }

    protected fun toMessageQuery(json: ByteArray): MessageQuery {
        return objectMapper.readValue(json)
    }

    protected fun toMessageResponse(json: ByteArray): MessageResponse {
        return objectMapper.readValue(json)
    }

}