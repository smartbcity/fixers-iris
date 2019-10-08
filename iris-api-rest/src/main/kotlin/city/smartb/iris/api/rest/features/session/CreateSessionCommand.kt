package city.smartb.iris.api.rest.features.session

import city.smartb.iris.api.rest.model.ChannelSession
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Queue
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class CreateSessionCommand(
        val sessionProvider: SessionProvider,
        val amqpAdmin: AmqpAdmin
) {

    private val logger = LoggerFactory.getLogger(CreateSessionCommand::class.java)

    fun execute(): Mono<CreateSessionResponse> {
        return Mono.create {
            val session = sessionProvider.create()
            logger.info("Create session[${session.channelId}]")
            val args = hashMapOf("x-expires" to Duration.ofMinutes(15).toMillis()) as Map<String, Object>
            val phoneQueueName = session.getQueueToSendToSigner()
            val phoneQueue = Queue(phoneQueueName, true, false, true, args);
            amqpAdmin.declareQueue(phoneQueue)
            logger.info("[${session.channelId}] queue[${phoneQueue}]")

            val browserQueueName = ChannelSession(session.channelId).getQueueToSendToApplication()
            val browserQueue = Queue(browserQueueName, true, false, true, args);
            amqpAdmin.declareQueue(browserQueue)
            logger.info("[${session.channelId}] queue[${browserQueue}]")

            it.success(CreateSessionResponse((session.channelId.id)))
        }
    }

}