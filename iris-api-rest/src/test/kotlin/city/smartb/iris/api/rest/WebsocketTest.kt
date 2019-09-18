package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.features.CreateResponse
import city.smartb.iris.api.rest.model.PublicKeyResponse
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
import org.springframework.web.reactive.socket.client.StandardWebSocketClient
import reactor.core.publisher.Mono
import java.net.URI


@ExtendWith(MockitoExtension::class)
class WebsocketTest : WebBaseTest() {

    val logger = LoggerFactory.getLogger(WebsocketTest::class.java)

    @Autowired
    lateinit var objectMessage: ObjectMapper

    @Test
    fun fullTest() {
        val createResponse: CreateResponse = webClient()
                .get()
                .uri("/create")
                .retrieve()
                .bodyToMono(CreateResponse::class.java)
                .block()!!

        Assertions.assertThat(createResponse).isNotNull()
        Assertions.assertThat(createResponse.sessionId).isNotNull()

        val uriApplication = URI.create("ws://localhost:$port/connect/application/${createResponse.sessionId}")
        val uriSigner = URI.create("ws://localhost:$port/connect/signer/${createResponse.sessionId}")

        connectWebSocket(createResponse, uriSigner, sendPublicKeyHandler(uriSigner)).subscribe()
        Thread.sleep(5000);
        connectWebSocket(createResponse, uriApplication, browserSocketHandler(uriApplication)).subscribe()
        Thread.sleep(10000)

    }

    fun connectWebSocket(createResponse: CreateResponse, uri: URI, webSocketHandler: (WebSocketSession) -> Mono<Void>): Mono<Void> {
        val client = ReactorNettyWebSocketClient()
        return client.execute(uri, webSocketHandler)
    }


    fun standardWebSocketClient(createResponse: CreateResponse, uri: URI, webSocketHandler: (WebSocketSession) -> Mono<Void>) {
        val client = StandardWebSocketClient()
        client.execute(uri, webSocketHandler).subscribe()
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

    private fun sendPublicKeyHandler(uri: URI) = { session: WebSocketSession ->
        session.send(Mono.just(session.textMessage(getPublicKeyResponseAsString(uri)))).then()
    }

    fun getPublicKeyResponseAsString(url: URI): String {
        return objectMessage.writeValueAsString(
                PublicKeyResponse(
                        application = "IrisTest",
                        publickey = "PublicKeyIrisTest"

                )
        )
    }
}

