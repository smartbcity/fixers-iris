package city.smartb.iris.api.rest.features.sim

import city.smartb.iris.api.rest.kannel.KannelClient
import city.smartb.iris.api.rest.model.ChannelSession
import city.smartb.iris.api.rest.features.session.ChannelProvider
import city.smartb.iris.api.rest.features.messages.SignerHandler
import org.springframework.stereotype.Service

@Service
class SimService(
        private val channelProvider: ChannelProvider,
        private val kannelClient: KannelClient,
        private val messagesHandler: SignerHandler) {

    fun start(channelSession: ChannelSession, phoneNumber: String) {
        val simChannelId = channelProvider.getOrCreateSimChannelId(channelSession.channelId, phoneNumber)
        val handler = SimHandler(channelSession = channelSession, kannelClient = kannelClient, messagesHandler = messagesHandler)
        channelSession.linkSimHandler(simChannelId, handler)
    }

    fun stop(channelSession: ChannelSession) {
        channelSession.simHandler?.stop()
    }

}