package city.smartb.iris.ld.vc

import city.smartb.iris.ld.jsonld.JsonLdObject
import city.smartb.iris.ld.jsonld.jackson.ToToJsonConvertorJackson
import city.smartb.iris.ld.ldproof.LdProof
import java.time.LocalDateTime

class VerifiableCredentialBuilder<T>(jsonLdObject: Map<String, Any>) : JsonLdObject(jsonLdObject) {
    private val convertorJackson: ToToJsonConvertorJackson = ToToJsonConvertorJackson()
    fun withId(id: String): VerifiableCredentialBuilder<T> {
        this.jsonLd.put(JsonLdObject.JSON_LD_ID, id)
        return this
    }

    fun withType(type: String): VerifiableCredentialBuilder<T> {
        this.jsonLd.put(JsonLdObject.JSON_LD_TYPE, type)
        return this
    }

    fun <Ojb> with(key: String, o: Ojb): VerifiableCredentialBuilder<T> {
        this.jsonLd.put(key, convertorJackson.toJson(o))
        return this
    }

    fun <Ojb> with(key: String, o: List<Ojb>?): VerifiableCredentialBuilder<T> {
        this.jsonLd.put(key, convertorJackson.toJson(o))
        return this
    }

    fun withContext(context: List<String>): VerifiableCredentialBuilder<T> {
        this.jsonLd.put(JsonLdObject.JSON_LD_CONTEXT, context)
        return this
    }

    fun withContextDefault(): VerifiableCredentialBuilder<T> {
        return withContext(listOf(VC_DEFAULT_CONTEXT))
    }

    fun withIssuanceDate(issuanceDate: LocalDateTime): VerifiableCredentialBuilder<T> {
        this.jsonLd.put(VerifiableCredential.VC_ISSUANCE_DATE, issuanceDate.toString())
        return this
    }

    fun withIssuanceDate(issuanceDate: String): VerifiableCredentialBuilder<T> {
        this.jsonLd.put(VerifiableCredential.VC_ISSUANCE_DATE, issuanceDate)
        return this
    }

    fun withIssuanceDateNow(): VerifiableCredentialBuilder<T> {
        return withIssuanceDate(LocalDateTime.now().toString())
    }

    fun withIssuer(issuer: String): VerifiableCredentialBuilder<T> {
        this.jsonLd.put(VerifiableCredential.VC_ISSUER, issuer)
        return this
    }

    fun withCredentialSubject(subject: T): VerifiableCredentialBuilder<T> {
        this.jsonLd.put(VerifiableCredential.VC_CREDENTIAL_SUBJECT, convertorJackson.toJson(subject))
        return this
    }

    fun withCredentialSubject(subject: List<T>): VerifiableCredentialBuilder<T> {
        val convertorJackson = ToToJsonConvertorJackson()
        this.jsonLd.put(VerifiableCredential.VC_CREDENTIAL_SUBJECT, convertorJackson.toJson(subject))
        return this
    }

    fun build(proof: LdProof): VerifiableCredential {
        this.jsonLd.put(LdProof.JSON_LD_PROOF, proof.asJson() as Any)
        return VerifiableCredential(this.jsonLd)
    }

    companion object {
        fun <T> create(): VerifiableCredentialBuilder<T> {
            return VerifiableCredentialBuilder(LinkedHashMap())
        }

        fun <T> fromMap(json: LinkedHashMap<String, Any>): VerifiableCredentialBuilder<T> {
            return VerifiableCredentialBuilder(json)
        }

        const val VC_DEFAULT_CONTEXT = "https://www.w3.org/2018/credentials/v1"
    }
}
