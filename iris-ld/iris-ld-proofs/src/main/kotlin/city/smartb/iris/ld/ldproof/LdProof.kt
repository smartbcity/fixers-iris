package city.smartb.iris.ld.ldproof

import city.smartb.iris.ld.jsonld.JsonLdObject
import city.smartb.iris.ld.jsonld.reader.JsonFieldReader
import city.smartb.iris.ld.ldproof.serializer.LdProofDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.time.LocalDateTime

@JsonDeserialize(using = LdProofDeserializer::class)
class LdProof : JsonLdObject {
    constructor(jsonLdObject: Map<String, Any>) : super(jsonLdObject) {}
    constructor(jsonLdObject: Map<String, Any>, fieldReader: JsonFieldReader) : super(jsonLdObject, fieldReader) {}

    companion object {
        fun fromMap(json: Map<String, Any>): LdProof {
            return LdProof(json)
        }

        const val JSON_LD_CONTEXT_SECURITY_V2 = "https://w3id.org/security/v2"
        const val JSON_LD_PROOF = "proof"
        const val JSON_LD_CREATED = "created"
        const val JSON_LD_DOMAIN = "domain"
        const val JSON_LD_CHALLENGE = "challenge"
        const val JSON_LD_PURPOSE = "proofPurpose"
        const val JSON_LD_VERIFICATION_METHOD = "verificationMethod"
        const val JSON_LD_SIGNATURE_VALUE = "signatureValue"
        const val JSON_LD_JWS = "jws"
    }

//    val proofPurpose: String
//        get() = get(JSON_LD_PURPOSE).asString()

    fun getProofPurpose(): String? {
        return get(JSON_LD_PURPOSE).asString()
    }
//
    fun getVerificationMethod(): String? {
        return get(JSON_LD_VERIFICATION_METHOD).asString()
    }

    fun getCreated(): LocalDateTime {
        return get(JSON_LD_CREATED).asLocalDateTime()
    }

//    val created: LocalDateTime
//        get() = get(JSON_LD_CREATED).asLocalDateTime()
//    val verificationMethod: String
//        get() = get(JSON_LD_VERIFICATION_METHOD).asString()
    val challenge: String?
        get() = get(JSON_LD_CHALLENGE).asString()
    val domain: String?
        get() = get(JSON_LD_DOMAIN).asString()
//    val type: String
//        get() = get(JSON_LD_TYPE).asString()
    val jws: String?
        get() = get(JSON_LD_JWS).asString()

//    fun asJson(): LinkedHashMap<String, Any> {
//        return LinkedHashMap<String, Any>(this.jsonLdObject)
//    }
}
