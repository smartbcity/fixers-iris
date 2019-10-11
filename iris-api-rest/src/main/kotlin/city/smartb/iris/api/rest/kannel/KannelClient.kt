package city.smartb.iris.api.rest.kannel

import city.smartb.iris.api.rest.config.logger
import city.smartb.iris.api.rest.model.ActionType
import city.smartb.iris.api.rest.model.MessageQuery
import city.smartb.iris.api.rest.model.SimChannelId
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import reactor.core.publisher.Mono

@Component
class KannelClient() {
    companion object {
        val KANNEL_URL = "http://kannel.smartb.network:13008/sms"
    }

    val log by logger()

    fun send(simChannelId: SimChannelId, messageQuery: MessageQuery): Mono<ResponseEntity<String>> {
        val url = "${KANNEL_URL}?to=${simChannelId.phoneNumber}&chid=${simChannelId.phoneChannelId}&text=${toText(messageQuery)}"
        log.info("Kannel call url[$url]")

        val resp = RestTemplate().getForEntity(url, String::class.java)
        return Mono.just(resp)
    }

    private fun toText(messageQuery: MessageQuery): String =
            when (messageQuery.action) {
                ActionType.SIGN -> "${messageQuery.action.index} ${messageQuery.payload["signature"]}"
                ActionType.SIGN_PUB_KEY -> "${messageQuery.action.index} ${messageQuery.payload.get("sha256")}"
                else -> "${messageQuery.action.index}"
            }

}