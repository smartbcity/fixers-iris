package city.smartb.iris.ld.vc

import city.smartb.iris.ld.jsonld.JsonLdObject
import city.smartb.iris.ld.jsonld.reader.JsonField
import city.smartb.iris.ld.jsonld.reader.JsonFieldReader
import city.smartb.iris.ld.ldproof.LdProof
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import vc.deserializer.VerifiableCredentialDeserializer

@JsonDeserialize(using = VerifiableCredentialDeserializer::class)
class VerifiableCredential : JsonLdObject {
    constructor(json: Map<String, Any>) : super(json)
    constructor(json: Map<String, Any>, reader: JsonFieldReader) : super(json, reader)


    companion object {
        fun from(json: LinkedHashMap<String, Any>): VerifiableCredential {
            return VerifiableCredential(json)
        }
        const val VC_ISSUER = "issuer"
        const val VC_ISSUANCE_DATE = "issuanceDate"
        const val VC_CREDENTIAL_SUBJECT = "credentialSubject"
    }

    val issuanceDate: String?
        get() = this.get(VC_ISSUANCE_DATE).asString()
    val issuer: String?
        get() = this.get(VC_ISSUER).asString()

    fun <T> getCredentialSubject(clazz: Class<T>?): T {
        return this.get(VC_CREDENTIAL_SUBJECT).asObject(clazz)
    }

    fun <T> getCredentialSubjectAsList(clazz: Class<T>?): List<T> {
        return this.get(VC_CREDENTIAL_SUBJECT).asListObjects(clazz)
    }

    fun <T> getCredentialSubject(): JsonField {
        return this.get(VC_CREDENTIAL_SUBJECT)
    }

    val proof: LdProof
        get() {
            val json: Map<String, Any> = this.get(LdProof.JSON_LD_PROOF).asMap()
            return LdProof.fromMap(json)
        }
}
