package city.smartb.iris.api.rest.features.session

import city.smartb.iris.api.rest.features.sim.SimService
import city.smartb.iris.api.rest.model.ChannelId
import city.smartb.iris.api.rest.model.ChannelSession
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Queue
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class DeleteChannelCommand(
        val channelProvider: ChannelProvider,
        val simService: SimService,
        val amqpAdmin: AmqpAdmin
) {

    private val logger = LoggerFactory.getLogger(DeleteChannelCommand::class.java)

    fun execute(channelId: ChannelId): Mono<Boolean> {
        return Mono.create {
            val session = channelProvider.fromChannelId(channelId)
            logger.info("Delete session[${channelId}]")

            simService.stop(session)
            channelProvider.delete(channelId)

            val phoneQueueName = session.getQueueToSendToSigner()
            logger.info("[${session.channelId}] Delete queue[${phoneQueueName}]")
            amqpAdmin.deleteQueue(phoneQueueName)


            val browserQueueName = ChannelSession(session.channelId).getQueueToSendToApplication()
            logger.info("[${session.channelId}] delete queue[${browserQueueName}]")
            amqpAdmin.deleteQueue(browserQueueName)

            it.success(true)
        }
    }

}