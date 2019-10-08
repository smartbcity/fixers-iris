package city.smartb.iris.api.rest.model

open class MessageResponse(
        override val action: ActionType,
        override val payload: Map<String, String> = mapOf()
): Message(action, Type.RESPONSE, payload)

open class PublicKeyResponse(
        publicKey: String
): MessageResponse(ActionType.PUB_KEY, mapOf("publicKey" to publicKey))

open class SignPublicKeyResponse(
        signature: String
): MessageResponse(ActionType.SIGN_PUB_KEY, mapOf("signature" to signature))

open class SignResponse(
        signature: String
): MessageResponse(ActionType.SIGN,  mapOf("signature" to signature))

class AuthMessageResponse (jwt: String): MessageQuery(
        action = ActionType.AUTH,
        type = Type.RESPONSE,
        payload = mapOf("jwt" to jwt)
)
