package city.smartb.iris.api.rest.utils

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import java.time.Duration

@ExtendWith(SpringExtension::class)
@SpringBootTest
class BaseTest {

    protected var port: Int = 8889

    protected var restTemplate: TestRestTemplate = TestRestTemplate()

    protected fun baseUrl(): UriComponentsBuilder {

        return UriComponentsBuilder.fromHttpUrl("http://localhost:$port")
    }

    protected fun webClient(): WebClient {
        return WebClient.create("http://localhost:$port")
    }

    protected fun webTestClient(): WebTestClient {
        return WebTestClient.bindToServer()
                .baseUrl("http://localhost:$port")
                .responseTimeout(Duration.ofMillis(60000))
                .build();
    }

}