package city.smartb.iris.api.rest.features.sim

import city.smartb.iris.api.rest.kannel.KannelClient
import city.smartb.iris.api.rest.model.ChannelSession
import city.smartb.iris.api.rest.features.session.ChannelProvider
import city.smartb.iris.api.rest.features.messages.SignerHandler
import city.smartb.iris.api.rest.model.SimChannelId
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SimService(
        private val channelProvider: ChannelProvider,
        private val kannelClient: KannelClient,
        private val messagesHandler: SignerHandler) {

    fun start(channelSession: ChannelSession, phoneNumber: String): Mono<SimChannelId> {
        return Mono.create {
            val simChannelId = channelProvider.getOrCreateSimChannelId(channelSession.channelId, phoneNumber)
            val handler = SimHandler(channelSession = channelSession, kannelClient = kannelClient, messagesHandler = messagesHandler)
            channelSession.linkSimHandler(simChannelId, handler)
            handler.start()
            it.success(simChannelId)
        }
    }

    fun stop(channelSession: ChannelSession) {
        channelSession.simHandler?.stop()
    }

}