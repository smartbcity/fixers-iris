package city.smartb.iris.api.rest.features

import city.smartb.iris.api.rest.model.Session
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Queue
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.*

@Component
class CreateSessionCommand(val amqpAdmin: AmqpAdmin) {

    fun execute(): Mono<CreateResponse> {
        return Mono.create {
            val uuid = UUID.randomUUID().toString()
            val args = hashMapOf("x-expires" to Duration.ofMinutes(15).toMillis()) as Map<String, Object>
            val phoneQueueName = Session(uuid).getQueueToSendToPhone()
            val phoneQueue = Queue(phoneQueueName, true, false, true, args);
            amqpAdmin.declareQueue(phoneQueue)

            val browserQueueName = Session(uuid).getQueueToSendToBrowser()
            val browserQueue = Queue(browserQueueName, true, false, true, args);
            amqpAdmin.declareQueue(browserQueue)

            it.success(CreateResponse((uuid)))
        }
    }

}