package city.smartb.iris.ld.did

import city.smartb.iris.ld.did.serializer.DIDVerificationMethodDeserializer
import city.smartb.iris.ld.jsonld.reader.JsonFieldReader
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(using = DIDVerificationMethodDeserializer::class)
class DIDVerificationMethod : ControledJsonLdObject {
    constructor(json: Map<String, Any>) : super(json) {}
    constructor() : super(LinkedHashMap<String, Any>()) {}
    constructor(json: Map<String, Any>, reader: JsonFieldReader) : super(json, reader) {}

    val publicKeyBase64: String?
        get() = this[JSON_LD_PUBLICKEYBASE64].asString()
    val publicKeyBase58: String?
        get() = this[JSON_LD_PUBLICKEYBASE58].asString()
    val publicKeyHex: String?
        get() = this[JSON_LD_PUBLICKEYHEX].asString()
    val publicKeyPem: String?
        get() = this[JSON_LD_PUBLICKEYPEM].asString()
    val publicKeyJwk: Any
        get() = this[JSON_LD_PUBLICKEYJWK].asMap()

    fun toJSON(): Any {
        return jsonLdObject
    }

    companion object {
        const val JSON_LD_PUBLICKEYBASE64 = "publicKeyBase64"
        const val JSON_LD_PUBLICKEYBASE58 = "publicKeyBase58"
        const val JSON_LD_PUBLICKEYHEX = "publicKeyHex"
        const val JSON_LD_PUBLICKEYPEM = "publicKeyPem"
        const val JSON_LD_PUBLICKEYJWK = "publicKeyJwk"
        const val RSA_VERIFICATION_2018 = "RsaVerificationKey2018"
    }
}
