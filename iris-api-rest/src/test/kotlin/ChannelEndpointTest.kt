import city.smartb.iris.api.rest.features.session.CreateChannelResponse
import city.smartb.iris.api.rest.utils.WebBaseTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.mockito.junit.jupiter.MockitoExtension

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension::class)
class ChannelEndpointTest : WebBaseTest() {

    private var valueSent = "signedNewValue";

    companion object {
        var getCmdIsSent: Boolean = false
    }

//    @Test
    fun create_shouldBeStartedFirst() {
        var uri = baseUrl().path("/channels").build().toUri()
        println("//////channels ${Thread.currentThread().name}")
        val session = this.restTemplate.postForObject(uri, null, CreateChannelResponse::class.java)

        Thread.sleep(2000)
        println("//////send  ${Thread.currentThread().name}")
        Assertions.assertThat(session.channelId).isNotBlank();
        Assertions.assertThat(getCmdIsSent).isTrue();
        webTestClient().post().uri("/channels/$session.sessionId/messages")
                .syncBody(valueSent)
                .exchange()

        Thread.sleep(1000)
        println("//////get  ${Thread.currentThread().name}")
        Assertions.assertThat(session).isNotNull();
        Assertions.assertThat(session.channelId).isNotBlank();
        getCmdIsSent = true
        webTestClient()
                .get()
                .uri("/channels/${session.channelId}/messages")
                .exchange()
                .expectBody()
                .consumeWith { response ->
                    Assertions.assertThat(response.getResponseBody()).isNotNull()
                    Assertions.assertThat(String(response.getResponseBody()!!)).isEqualTo(valueSent)
                }


    }
}