package city.smartb.iris.api.rest.model

import city.smartb.iris.api.rest.jwt.Jwt
import com.sun.tools.javac.main.Option

class Session(
        val id: String
) {

    companion object {
        val currentkyAuth = hashMapOf<String, Jwt>()
    }

    fun getQueueToSendToSigner() = "$id.forPhone"
    fun getQueueToSendToApplication() = "$id.forBrowser"

    fun getQueueToSendObjectFrom(value: TransitValue) = when {
        value.isMessage() -> getQueueToSendToSigner()
        value.isResponse() -> getQueueToSendToApplication()
        else -> throw Option.InvalidValueException("Invalid value object[${value}]")
    }

    @Synchronized
    fun setJWTKey(jwt: Jwt) {
        currentkyAuth.put(id, jwt)
    }

    @Synchronized
    fun getJWTKey(): Jwt? = currentkyAuth.get(id)


}