package city.smartb.iris.api.rest.features.sim

import city.smartb.iris.api.rest.config.logger
import city.smartb.iris.api.rest.kannel.KannelClient
import city.smartb.iris.api.rest.model.ChannelSession
import city.smartb.iris.api.rest.model.MessageResponse
import city.smartb.iris.api.rest.features.messages.SignerHandler
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import reactor.core.Disposable
import reactor.core.publisher.Flux
import reactor.netty.http.client.HttpClientResponse

open class SimHandler(
        private val channelSession: ChannelSession,
        private val kannelClient: KannelClient,
        private val messagesHandler: SignerHandler) {


    private lateinit var flux: Disposable
    private val log by logger()

    fun start() {
        this.flux = listenMessageBrokerToSendToKannel().subscribe()
    }

    fun stop() {
        this.flux.dispose()
    }

    private fun listenMessageBrokerToSendToKannel(): Flux<ResponseEntity<String>> {
        return messagesHandler.transferToDevice(channelSession).map {
            log.info("Message[${it}] Send to kannel")
            kannelClient.send(simChannelId = channelSession.simChannelId!!, messageQuery = it).block()
        }
    }

    fun receive(receive: MessageResponse, channelSession: ChannelSession) {
        return messagesHandler.receiveFromDevice(channelSession, receive)
    }

}