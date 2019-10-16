package city.smartb.iris.api.rest.features.session

import city.smartb.iris.api.rest.model.ChannelId
import city.smartb.iris.api.rest.model.ChannelSession
import city.smartb.iris.api.rest.model.SimChannelId
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketSession
import kotlin.collections.HashMap

@Component
class ChannelProvider {

    companion object {
        private val ALL_SIM_CHANNEL_ID: HashMap<PhoneChannelId, SimChannelId> = HashMap()
        private val ALL_CHANNEL_SESSION: HashMap<ChannelId, ChannelSession> = HashMap()
    }

    fun fromWebSocket(webSocketSession: WebSocketSession): ChannelSession {
        val uri = webSocketSession.handshakeInfo.uri
        val channelId = uri.path.split("/").last()
        return fromChannelId(ChannelId(channelId))
    }

    fun fromChannelId(id: ChannelId): ChannelSession {
        return ALL_CHANNEL_SESSION.getOrPut(id, { ChannelSession(id) })
    }

    fun fromSimChannelId(simChannelId: SimChannelId): ChannelSession? {
        val phoneChannelId = ALL_SIM_CHANNEL_ID.filterValues { it == simChannelId }.keys.first()
        return fromChannelId(phoneChannelId.id)
    }

    fun create(): ChannelSession {
        val chId = ChannelId.new()
        val session = ChannelSession(chId)
        ALL_CHANNEL_SESSION[chId] = session
        return session
    }

    fun getOrCreateSimChannelId(id: ChannelId, phoneNumber: String): SimChannelId {
       return ALL_SIM_CHANNEL_ID.getOrPut(PhoneChannelId(id, phoneNumber), {
           SimChannelId( phoneNumber = phoneNumber, phoneChannelId = ALL_SIM_CHANNEL_ID.size)
       })
    }

    fun delete(channelId: ChannelId) {
        ALL_SIM_CHANNEL_ID.keys.filter {
            it.id == channelId
        }.forEach {
            ALL_SIM_CHANNEL_ID.remove(it)
        }
        ALL_CHANNEL_SESSION.remove(channelId)
    }
}

data class PhoneChannelId(
        val id: ChannelId,
        val phoneNumber: String
)