package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.features.session.ChannelResponse
import city.smartb.iris.api.rest.utils.WebBaseTest
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient
import reactor.core.publisher.Mono
import java.net.URI


@ExtendWith(MockitoExtension::class)
class WebsocketTest : WebBaseTest() {

    val logger = LoggerFactory.getLogger(WebsocketTest::class.java)

    @Autowired
    lateinit var objectMessage: ObjectMapper

    @Test
    fun fullTest() {
        val response: ChannelResponse = webClient()
                .post()
                .uri("/channels")
                .exchange()
                .expectBody<ChannelResponse>()
                .returnResult().responseBody!!

        Assertions.assertThat(response).isNotNull()
        Assertions.assertThat(response.channelId).isNotNull()

        val uriSigner = URI.create("ws://localhost:8889/connect/signer/0cf7a2c1-db32-4623-a2ea-784401877cc7")

        connectWebSocket(response, uriSigner, browserSocketHandler(uriSigner)).subscribe()
        Thread.sleep(5000);
    }

    fun connectWebSocket(response: ChannelResponse, uri: URI, webSocketHandler: (WebSocketSession) -> Mono<Void>): Mono<Void> {
        val client = ReactorNettyWebSocketClient()
        return client.execute(uri, webSocketHandler)
    }


    private fun browserSocketHandler(uri: URI) = { session: WebSocketSession ->
        session
                .receive()
                .map<String> {
                    logger.info("/////////////////////from test browser Message[${it.payloadAsText}]")
                    it.payloadAsText
                }
                .doOnSubscribe { System.out.println(".OPEN") }
                .doFinally { System.out.println(".CLOSE") }
                .then()


    }

}

