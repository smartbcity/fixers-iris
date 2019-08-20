package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.utils.WebBaseTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.mockito.junit.jupiter.MockitoExtension

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension::class)
class ConnectApiAsyncTest : WebBaseTest() {

    private var valueSent = "signedNewValue";

    companion object {
        var id: String? = null
        var getCmdIsSent: Boolean = false
    }

    @Test
    fun create_shouldBeStartedFirst() {
        var uri = baseUrl().path("/create").build().toUri()
        println("//////create ${Thread.currentThread().name}")
        id = this.restTemplate.getForObject(uri, String::class.java)
    }

    @Test
    fun get_mustBeCalledBeforeSend() {
        Thread.sleep(1000)
        println("//////get  ${Thread.currentThread().name}")
        Assertions.assertThat(id).isNotBlank();
        getCmdIsSent = true
        webTestClient()
                .get()
                .uri("/get/$id")
                .exchange()
                .expectBody()
                .consumeWith { response ->
                    Assertions.assertThat(response.getResponseBody()).isNotNull()
                    Assertions.assertThat(String(response.getResponseBody()!!)).isEqualTo(valueSent)
                }
    }

    @Test
    fun send_shouldBeExecutedTheLast() {
        Thread.sleep(2000)
        println("//////send  ${Thread.currentThread().name}")
        Assertions.assertThat(id).isNotBlank();
        Assertions.assertThat(getCmdIsSent).isTrue();
        webTestClient().post().uri("/send/$id")
                .syncBody(valueSent)
                .exchange()
    }
}