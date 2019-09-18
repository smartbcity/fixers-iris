package city.smartb.iris.api.rest.model


open class Response(
        open val action: ActionType,
        open val application: String,
        open val payload: Map<String, String>
) {
    override fun toString(): String {
        return "Response(action=$action, application='$application', payload=$payload)"
    }
}

class PublicKeyResponse(
        override val application: String,
        val publickey: String
) : Response(
        action = ActionType.AUTH,
        application = application,
        payload = mapOf("publicKey" to publickey)
)

class AuthResponse(
        override val application: String,
        val jwt: String
) : Response(
        action = ActionType.AUTH,
        application = application,
        payload = mapOf("jwt" to jwt)
)

class SignResponse(
        override val application: String,
        val signature: String
) : Response(
        action = ActionType.SIGN,
        application = application,
        payload = mapOf("signature" to signature)
        )