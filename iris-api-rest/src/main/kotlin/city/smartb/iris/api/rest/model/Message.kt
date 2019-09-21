package city.smartb.iris.api.rest.model

enum class ActionType {
    AUTH, PUB_KEY, SIGN, SIGN_PUB_KEY, SIGN_JWT
}

enum class Type {
    QUERY, RESPONSE
}

open class Message(
        open val action: ActionType,
        open val type: Type,
        open val payload: Map<String, String> = mapOf()
) {

    fun isQuery() = type == Type.QUERY
    fun isResponse() = type == Type.RESPONSE

    override fun toString(): String {
        return "Message(action=$action, payload=$payload)"
    }
}

open class MessageQuery(
        override val action: ActionType,
        override val type: Type,
        override val payload: Map<String, String> = mapOf()
): Message(action, Type.QUERY, payload)

open class MessageResponse(
        override val action: ActionType,
        override val type: Type,
        override val payload: Map<String, String> = mapOf()
): Message(action, Type.RESPONSE, payload)


class PubKeyMessageQuery: MessageQuery(
        action = ActionType.PUB_KEY,
        type = Type.QUERY,
        payload = mapOf()
)

class SignPubKeyMessageQuery (val sha256: String): MessageQuery(
        action = ActionType.SIGN_PUB_KEY,
        type = Type.QUERY,
        payload = mapOf("sha256" to sha256)
)

class AuthMessageResponse (jwt: String): MessageQuery(
        action = ActionType.AUTH,
        type = Type.RESPONSE,
        payload = mapOf("jwt" to jwt)
)
