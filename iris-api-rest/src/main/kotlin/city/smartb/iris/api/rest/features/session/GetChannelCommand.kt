package city.smartb.iris.api.rest.features.session

import city.smartb.iris.api.rest.model.ChannelId
import city.smartb.iris.api.rest.model.ChannelSession
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Component
class GetChannelCommand(
        private val channelProvider: ChannelProvider,
        private val amqpAdmin: AmqpAdmin
) {

    private val logger = LoggerFactory.getLogger(GetChannelCommand::class.java)

    fun execute(channelId: ChannelId): Mono<ChannelResponse> {
        return Mono.defer {
            val status  = getStatus(channelId)
            ChannelResponse(
                    channelId = channelId.id,
                    status = status
            ).toMono()
        }
    }

    fun getStatus(channelId: ChannelId): ChannelStatus {
        val isSessionExists = channelProvider.isExists(channelId)
        if(!isSessionExists)
            return ChannelStatus.NONE

        val session = channelProvider.fromChannelId(channelId)
        val signerQueueProperties = amqpAdmin.getQueueProperties(session.getQueueToSendToSigner())
        val applicationQueueProperties = amqpAdmin.getQueueProperties(session.getQueueToSendToApplication())
        if(signerQueueProperties == null || applicationQueueProperties == null)
            return ChannelStatus.EXPIRED

        return ChannelStatus.ALIVE
    }

}