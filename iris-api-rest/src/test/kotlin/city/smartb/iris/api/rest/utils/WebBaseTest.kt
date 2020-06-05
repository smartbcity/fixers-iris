package city.smartb.iris.api.rest.utils

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient
import org.springframework.web.reactive.socket.client.WebSocketClient
import org.springframework.web.util.UriComponentsBuilder
import java.time.Duration
import org.springframework.web.reactive.socket.WebSocketMessage
import reactor.core.publisher.Mono
import java.net.URI


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebBaseTest {

    @LocalServerPort
    protected lateinit var port: Integer

    @Autowired
    protected lateinit var restTemplate: TestRestTemplate


    protected fun baseUrl(): UriComponentsBuilder {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:$port")
    }

    protected fun webClient(): WebTestClient {
        return WebTestClient
                .bindToServer()
                .responseTimeout(Duration.ofSeconds(20))
                .baseUrl(baseUrl().toUriString())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
    }


    protected fun webTestClient(): WebTestClient {
        return WebTestClient.bindToServer()
                .baseUrl("http://localhost:$port")
                .responseTimeout(Duration.ofMillis(60000))
                .build();
    }

}