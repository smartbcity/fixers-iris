package city.smartb.iris.api.rest.model

import city.smartb.iris.api.rest.model.jwt.Jwt
import city.smartb.iris.api.rest.features.sim.SimHandler

class ChannelSession(
        val channelId: ChannelId,
        var simChannelId: SimChannelId? = null,
        var simHandler: SimHandler? = null
) {

    companion object {
        val currentkyAuth = hashMapOf<ChannelId, Jwt>()
    }

    fun getQueueToSendToSigner(): String {
        return "${channelId.id}.forPhone"
    }

    fun getQueueToSendToApplication(): String {
        return "${channelId.id}.forBrowser"
    }

    @Synchronized
    fun setJWTKey(jwt: Jwt) {
        currentkyAuth.put(channelId, jwt)
    }

    @Synchronized
    fun getJWTKey(): Jwt? = currentkyAuth.get(channelId)

    fun linkSimHandler(simChannelId: SimChannelId, handler: SimHandler) {
        this.simChannelId = simChannelId
        this.simHandler = handler
    }
}