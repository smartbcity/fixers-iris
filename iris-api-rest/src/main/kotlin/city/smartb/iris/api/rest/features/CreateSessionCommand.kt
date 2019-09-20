package city.smartb.iris.api.rest.features

import city.smartb.iris.api.rest.model.Session
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Queue
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.*

@Component
class CreateSessionCommand(val amqpAdmin: AmqpAdmin) {

    private val logger = LoggerFactory.getLogger(CreateSessionCommand::class.java)

    fun execute(): Mono<CreateResponse> {
        return Mono.create {
            val uuid = UUID.randomUUID().toString()
            logger.info("Create session[${uuid}]")
            val args = hashMapOf("x-expires" to Duration.ofMinutes(15).toMillis()) as Map<String, Object>
            val phoneQueueName = Session(uuid).getQueueToSendToSigner()
            val phoneQueue = Queue(phoneQueueName, true, false, true, args);
            amqpAdmin.declareQueue(phoneQueue)
            logger.info("[${uuid}] queue[${phoneQueue}]")

            val browserQueueName = Session(uuid).getQueueToSendToApplication()
            val browserQueue = Queue(browserQueueName, true, false, true, args);
            amqpAdmin.declareQueue(browserQueue)
            logger.info("[${uuid}] queue[${browserQueueName}]")

            it.success(CreateResponse((uuid)))
        }
    }

}