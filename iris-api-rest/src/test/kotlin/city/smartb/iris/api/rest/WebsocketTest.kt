package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.features.session.CreateChannelResponse
import city.smartb.iris.api.rest.utils.WebBaseTest
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
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
        val createResponse: CreateChannelResponse = webClient()
                .post()
                .uri("/channels")
                .retrieve()
                .bodyToMono(CreateChannelResponse::class.java)
                .block()!!

        Assertions.assertThat(createResponse).isNotNull()
        Assertions.assertThat(createResponse.channelId).isNotNull()

        val uriSigner = URI.create("ws://localhost:8889/connect/signer/0cf7a2c1-db32-4623-a2ea-784401877cc7")

        connectWebSocket(createResponse, uriSigner, browserSocketHandler(uriSigner)).subscribe()
        Thread.sleep(5000);
    }

    fun connectWebSocket(createResponse: CreateChannelResponse, uri: URI, webSocketHandler: (WebSocketSession) -> Mono<Void>): Mono<Void> {
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

