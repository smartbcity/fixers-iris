package city.smartb.iris.api.rest.features.sim

import city.smartb.iris.api.rest.kannel.KannelClient
import city.smartb.iris.api.rest.model.ChannelSession
import city.smartb.iris.api.rest.model.MessageResponse
import city.smartb.iris.api.rest.features.messages.SignerHandler
import org.slf4j.LoggerFactory
import reactor.core.Disposable
import reactor.core.publisher.Flux
import reactor.netty.http.client.HttpClientResponse

open class SimHandler(
        private val channelSession: ChannelSession,
        private val kannelClient: KannelClient,
        private val messagesHandler: SignerHandler) {

    private lateinit var flux: Disposable
    private val logger = LoggerFactory.getLogger(this.javaClass::class.java)


    fun start() {
        this.flux = listenMessageBrokerToSendToKannel().subscribe()
    }

    fun stop() {
        this.flux.dispose()
    }

    private fun listenMessageBrokerToSendToKannel(): Flux<HttpClientResponse> {
        return messagesHandler.transferToDevice(channelSession).map {
            kannelClient.send(simChannelId = channelSession.simChannelId!!, messageQuery = it).block()
        }
    }

    fun receive(receive: MessageResponse, channelSession: ChannelSession) {
        return messagesHandler.receiveFromDevice(channelSession, receive)
    }

}