package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.utils.WebBaseTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ConnectApiTest : WebBaseTest() {

    @Test
    fun fullTest() {
        val id: String = webClient()
                .get()
                .uri("/create")
                .retrieve()
                .bodyToMono(String::class.java)
                .block()!!

        Assertions.assertThat(id).isNotNull()

        var valueSent = "signedValue";
        webTestClient().post().uri("/send/$id").syncBody(valueSent).exchange()

        webTestClient()
                .get()
                .uri("/get/$id")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .consumeWith { response ->
                    Assertions.assertThat(response.getResponseBody()).isNotNull()
                    Assertions.assertThat(String(response.getResponseBody()!!)).isEqualTo(valueSent)
                }

    }
}