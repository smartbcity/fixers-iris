package city.smartb.iris.api.rest.features.session

data class ChannelResponse(
        val channelId: String,
        val status: ChannelStatus
)

enum class ChannelStatus{
    CREATED, ALIVE, EXPIRED, NONE
}