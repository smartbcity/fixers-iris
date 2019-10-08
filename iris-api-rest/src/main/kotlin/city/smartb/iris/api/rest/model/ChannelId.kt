package city.smartb.iris.api.rest.model

import java.util.*

data class ChannelId(
       val id: String
) {
    companion object {
        fun new(): ChannelId {
            val uuid = UUID.randomUUID().toString()
            return ChannelId(uuid)
        }
    }
}