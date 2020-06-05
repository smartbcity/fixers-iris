package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.features.session.ChannelResponse
import city.smartb.iris.api.rest.features.session.ChannelStatus
import city.smartb.iris.api.rest.utils.WebBaseTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList
import java.util.*

class ChannelEndpointAsyncTest : WebBaseTest() {


    companion object {
        const val CREATE_URI = "channels"
        fun GET_URI(chanelId: String) = "/channels/${chanelId}"
    }

    @Test
    fun create_shouldBeStartedFirst() {
        var uriCreate = baseUrl().path("/channels").build().toUri()
        val session = this.restTemplate.postForObject(uriCreate, null, ChannelResponse::class.java)
        var uriDelete = baseUrl().path("/channels/${session.channelId}").build().toUri()
        this.restTemplate.delete(uriDelete)
    }

    @Test
    fun get_shouldReturnNoneState_WhenChannelNotExists() {
        val channelId = UUID.randomUUID().toString()
        val response = getChannel(channelId)

        Assertions.assertThat(response.status).isEqualTo(ChannelStatus.NONE)
    }



    @Test
    fun get_shouldReturnAliveState_WhenChannelExists() {
        val chanelCreated = createChannel()

        val response = getChannel(chanelCreated.channelId)


        Assertions.assertThat(response.status).isEqualTo(ChannelStatus.ALIVE)
    }

    private fun createChannel(): ChannelResponse {
        return webClient()
                .post()
                .uri(CREATE_URI)
                .exchange().expectStatus().isOk
                .expectBody<ChannelResponse>()
                .returnResult().responseBody!!
    }

    private fun getChannel(channelId: String): ChannelResponse {
        return webClient()
                .get()
                .uri(GET_URI(channelId))
                .exchange().expectStatus().isOk
                .expectBody<ChannelResponse>()
                .returnResult().responseBody!!
    }
}

