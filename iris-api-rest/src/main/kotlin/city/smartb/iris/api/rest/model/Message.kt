package city.smartb.iris.api.rest.model

enum class ActionType {
    AUTH, AUTH_PUB_KEY, SIGN, SIGN_PUB_KEY,
}

open class Message(
        open val action: ActionType,
        open val url: String,
        open val application: String,
        open val payload: Map<String, String> = mapOf()
) : TransitValue {
    override fun toString(): String {
        return "Message(action=$action, url='$url', application='$application', payload=$payload)"
    }
}

class SignMessage(
        override val url: String,
        override val application: String,
        val sha256: String
) : Message(
        action = ActionType.SIGN,
        url = url,
        application = application,
        payload = mapOf("sha256" to sha256)
)

class AuthMessage(
        override val url: String,
        override val application: String
) : Message(
        action = ActionType.AUTH,
        application = application,
        url = url,
        payload = mapOf()
)