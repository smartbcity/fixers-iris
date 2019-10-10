package city.smartb.iris.api.rest.websocket

import city.smartb.iris.api.rest.model.Message
import city.smartb.iris.api.rest.model.ChannelSession
import city.smartb.iris.api.rest.features.session.ChannelProvider
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

open class AbstractReactiveWebSocketHandler<RECEIVE  : Message, SEND : Message, HANDLER : AbstractHandler<RECEIVE, SEND>>(
        private val channelProvider: ChannelProvider,
        private val objectMapper: ObjectMapper,
        private val messagesHandler: HANDLER) : WebSocketHandler {

    private val logger = LoggerFactory.getLogger(this.javaClass::class.java)

    override fun handle(webSocketSession: WebSocketSession): Mono<Void> {
        logger.info("Create session:" +webSocketSession.id)
        webSocketSession.attributes.forEach {
            logger.info("[${it.key}] => ${it.value}")
        }
        val session = channelProvider.fromWebSocket(webSocketSession)
        return webSocketSession.send(send(webSocketSession, session)).and(receive(webSocketSession.receive(), session))
    }

    private fun send(webSocketSession: WebSocketSession, channelSession: ChannelSession): Flux<WebSocketMessage> {
        return messagesHandler.transferToDevice(channelSession).map {
            val json = objectMapper.writeValueAsString(it)
            webSocketSession.textMessage(json)
        }
    }

    private fun receive(receive: Flux<WebSocketMessage>, channelSession: ChannelSession): Mono<Void> {
        return receive
                .map { it.getPayloadAsText() }
                .map { messagesHandler.toValueReceivedFromDevice(it.toByteArray()) }
                .map { messagesHandler.receiveFromDevice(channelSession, it) }
                .doOnError {
                    logger.error("Error handling the message", it)
                }
                .then()
    }

}