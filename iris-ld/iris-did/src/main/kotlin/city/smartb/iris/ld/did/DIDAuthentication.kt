package city.smartb.iris.ld.did

class DIDAuthentication(private val json: Any) {
    val publicKeyId: String?
        get() {
            if (json is String) {
                return json
            }
            return if (json is DIDVerificationMethod) {
                json.id
            } else null
        }

    fun toJSON(): Any {
        return json
    }
}
