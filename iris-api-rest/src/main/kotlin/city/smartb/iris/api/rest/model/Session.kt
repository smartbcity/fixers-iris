package city.smartb.iris.api.rest.model

import city.smartb.iris.api.rest.jwt.Jwt

class Session(
        val id: String
) {

    companion object {
        val currentkyAuth = hashMapOf<String, Jwt>()
    }

    fun getQueueToSendToSigner() = "$id.forPhone"
    fun getQueueToSendToApplication() = "$id.forBrowser"

    @Synchronized
    fun setJWTKey(jwt: Jwt) {
        currentkyAuth.put(id, jwt)
    }

    @Synchronized
    fun getJWTKey(): Jwt? = currentkyAuth.get(id)


}