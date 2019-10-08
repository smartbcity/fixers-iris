package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.features.session.CreateSessionResponse
import city.smartb.iris.api.rest.model.ActionType
import city.smartb.iris.api.rest.model.MessageResponse
import city.smartb.iris.api.rest.model.Type
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
class WebsocketPhoneClientTest : WebBaseTest() {

    val logger = LoggerFactory.getLogger(WebsocketPhoneClientTest::class.java)

    @Autowired
    lateinit var objectMessage: ObjectMapper

    @Test
    fun fullTest() {
        val createResponse: CreateSessionResponse = webClient()
                .post()
                .uri("/channels")
                .retrieve()
                .bodyToMono(CreateSessionResponse::class.java)
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

    fun connectWebSocket(createResponse: CreateSessionResponse, uri: URI, webSocketHandler: (WebSocketSession) -> Mono<Void>): Mono<Void> {
        val client = ReactorNettyWebSocketClient()
        return client.execute(uri, webSocketHandler)
    }


    fun standardWebSocketClient(createResponse: CreateSessionResponse, uri: URI, webSocketHandler: (WebSocketSession) -> Mono<Void>) {
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
                MessageResponse(
                        action = ActionType.PUB_KEY,
                        payload = mapOf("publicKey" to "publicKeyValue")
                )
        )
    }
}

