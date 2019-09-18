package city.smartb.iris.api.rest.websocket

import city.smartb.iris.api.rest.utils.WebBaseTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient
import reactor.core.publisher.Mono
import java.net.URI
import java.time.Duration

internal class ReactiveWebSocketHandlerTest: WebBaseTest() {

    @Test
    fun handle() {
        val uri = URI.create( "ws://localhost:$port/event-emitter")

        val client = ReactorNettyWebSocketClient()
        client.execute(
                uri
        ) { session ->
            session.send(
                    Mono.just(session.textMessage("event-spring-reactive-client-websocket")))
                    .thenMany(session.receive()
                            .map<String>{ it.getPayloadAsText() }
                            .log())
                    .then()
        }
                .block(Duration.ofSeconds(10L))
    }
}