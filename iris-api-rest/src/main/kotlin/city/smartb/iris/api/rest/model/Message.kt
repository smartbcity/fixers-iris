package city.smartb.iris.api.rest.model

enum class ActionType(val index: Int) {
    AUTH(0),
    PUB_KEY(1),
    SIGN(2),
    SIGN_PUB_KEY(3),
    CREDENTIAL_REQUEST(4),
    CREDENTIAL_OFFER(5),
    CREDENTIAL_TRANSFER(6);

    companion object {
        fun valueOf(value: Int) = values().find { it.index == value }
    }
}

enum class Type {
    QUERY, RESPONSE
}

open class Message(
        open val action: ActionType,
        open val type: Type,
        open val payload: Map<String, Any> = mapOf()
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
        override val payload: Map<String, Any> = mapOf()
) : Message(action, Type.QUERY, payload)


class PubKeyMessageQuery : MessageQuery(
        action = ActionType.PUB_KEY,
        type = Type.QUERY,
        payload = mapOf()
)

data class SignPubKeyMessageQuery(val sha256: String) : MessageQuery(
        action = ActionType.SIGN_PUB_KEY,
        type = Type.QUERY,
        payload = mapOf("sha256" to sha256)
)
