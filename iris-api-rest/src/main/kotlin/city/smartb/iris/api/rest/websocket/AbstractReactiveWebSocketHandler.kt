package city.smartb.iris.api.rest.websocket

import city.smartb.iris.api.rest.model.Session
import city.smartb.iris.api.rest.model.TransitValue
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

open class AbstractReactiveWebSocketHandler<RECEIVE  : TransitValue, SEND : TransitValue, HANDLER : AbstractHandler<RECEIVE, SEND>>(
        private val objectMapper: ObjectMapper,
        private val messagesHandler: HANDLER) : WebSocketHandler {

    private val logger = LoggerFactory.getLogger(this.javaClass::class.java)

    override fun handle(webSocketSession: WebSocketSession): Mono<Void> {
        val session = getSessionId(webSocketSession)
        return webSocketSession.send(send(webSocketSession, session)).and(receive(webSocketSession.receive(), session.id))
    }

    private fun send(webSocketSession: WebSocketSession, session: Session): Flux<WebSocketMessage> {
        return messagesHandler.transferToDevice(session).map {
            val json = objectMapper.writeValueAsString(it)
            webSocketSession.textMessage(json)
        }
    }

    private fun receive(receive: Flux<WebSocketMessage>, sessionId: String): Mono<Void> {
        val session = Session(sessionId)
        return receive
                .map { it.getPayloadAsText() }
                .map { messagesHandler.toValueReceivedFromDevice(it.toByteArray()) }
                .map { messagesHandler.receiveFromDevice(session, it) }
                .doOnError {
                    logger.error("Error handling the message", it)
                }
                .then()
    }

    private fun getSessionId(webSocketSession: WebSocketSession): Session {
        val uri = webSocketSession.handshakeInfo.uri
        val sessionId = uri.path.split("/").last()
        return Session(sessionId)
    }

}