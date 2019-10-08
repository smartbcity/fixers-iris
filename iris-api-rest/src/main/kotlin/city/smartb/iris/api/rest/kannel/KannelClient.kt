package city.smartb.iris.api.rest.kannel

import city.smartb.iris.api.rest.model.ActionType
import city.smartb.iris.api.rest.model.MessageQuery
import city.smartb.iris.api.rest.model.SimChannelId
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import reactor.netty.http.client.HttpClientResponse

@Component
class KannelClient {
    companion object {
        val KANNEL_URL = "http://kannel.smartb.network:13008/sms"
    }

    fun send(simChannelId: SimChannelId, messageQuery: MessageQuery): Mono<HttpClientResponse> {
        return HttpClient.create()
                .baseUrl("${KANNEL_URL}?to=${simChannelId.phoneNumber}&chid=${simChannelId.phoneChannelId}&text=${toText(messageQuery)}")
                .get()
                .response();
    }

    private fun toText(messageQuery: MessageQuery): String =
            when (messageQuery.action) {
                ActionType.SIGN -> "${messageQuery.action.index} ${messageQuery.payload["signature"]}"
                ActionType.SIGN_PUB_KEY -> "${messageQuery.action.index} ${messageQuery.payload.get("publicKey")}"
                else -> "${messageQuery.action.index}"
            }

}