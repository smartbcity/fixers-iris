package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.features.session.CreateChannelResponse
import city.smartb.iris.api.rest.utils.WebBaseTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.mockito.junit.jupiter.MockitoExtension

class ChannelEndpointAsyncTest : WebBaseTest() {

    @Test
    fun create_shouldBeStartedFirst() {
        var uriCreate = baseUrl().path("/channels").build().toUri()
        val session = this.restTemplate.postForObject(uriCreate, null, CreateChannelResponse::class.java)
        var uriDelete = baseUrl().path("/channels/${session.channelId}").build().toUri()
        this.restTemplate.delete(uriDelete)
    }
}