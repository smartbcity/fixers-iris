package city.smartb.iris.ld.ldproof

import city.smartb.iris.crypto.dsl.signer.Signer
import city.smartb.iris.ld.jsonld.JsonLdConsts
import city.smartb.iris.ld.jsonld.JsonLdObject
import city.smartb.iris.ld.jsonld.JsonLdObject.Companion.JSON_LD_TYPE
import city.smartb.iris.ld.ldproof.util.CanonicalizationUtil
import java.time.LocalDateTime

class LdProofBuilder {
    private val json: LinkedHashMap<String, Any>

    constructor() {
        json = LinkedHashMap()
    }

    constructor(json: Map<String, Any>) {
        this.json = LinkedHashMap(json)
    }

    fun withProofPurpose(proofPurpose: String): LdProofBuilder {
        json[LdProof.JSON_LD_PURPOSE] = proofPurpose
        return this
    }

    fun withCreated(created: LocalDateTime): LdProofBuilder {
        withCreated(created.toString())
        return this
    }

    fun withCreated(created: String): LdProofBuilder {
        json[LdProof.JSON_LD_CREATED] = created
        return this
    }

    fun withCreatedNow(): LdProofBuilder {
        withCreated(LocalDateTime.now())
        return this
    }

    fun withVerificationMethod(verificationMethod: String): LdProofBuilder {
        json[LdProof.JSON_LD_VERIFICATION_METHOD] = verificationMethod
        return this
    }

    fun withChallenge(challenge: String): LdProofBuilder {
        json[LdProof.JSON_LD_CHALLENGE] = challenge
        return this
    }

    fun withDomain(domain: String): LdProofBuilder {
        json[LdProof.JSON_LD_DOMAIN] = domain
        return this
    }

    fun withType(type: String): LdProofBuilder {
        json[JSON_LD_TYPE] = type
        return this
    }

    fun withContext(context: List<String>): LdProofBuilder {
        json[JsonLdObject.JSON_LD_CONTEXT] = context
        return this
    }

    fun withContextDefault(): LdProofBuilder {
        return withContext(listOf<String>(LdProof.JSON_LD_CONTEXT_SECURITY_V2))
    }

    fun canonicalize(): String {
        return CanonicalizationUtil.buildCanonicalizedDocument(json)
    }

    fun canonicalize(signer: Signer): String {
        json[JsonLdObject.JSON_LD_TYPE] = signer.term
        return CanonicalizationUtil.buildCanonicalizedDocument(json)
    }

    private fun addSecurityContextToJsonLdObject(jsonLdObject: LinkedHashMap<String, Any>) {
        val context = jsonLdObject[JsonLdConsts.CONTEXT]
        val contexts = getContexts(context)
        if (context is String) {
            contexts.add(context)
        }
        if (!contexts.contains(LdProof.JSON_LD_CONTEXT_SECURITY_V2)) {
            contexts.add(LdProof.JSON_LD_CONTEXT_SECURITY_V2)
        }
        jsonLdObject[JsonLdConsts.CONTEXT] = contexts
    }

    private fun getContexts(context: Any?): MutableList<Any> {
        return if (context is List<*>) {
            context as MutableList<Any>
        } else ArrayList()
    }

    fun build(jws: String): LdProof {
        json[LdProof.JSON_LD_JWS] = jws
        return LdProof.fromMap(json)
    }

    companion object {
        fun builder(): LdProofBuilder {
            return LdProofBuilder()
        }

        fun fromLdProof(ldProof: LdProof): LdProofBuilder {
            val json = ldProof!!.asJson() as MutableMap
            json!!.remove(LdProof.JSON_LD_JWS)
            json.remove(LdProof.JSON_LD_SIGNATURE_VALUE)
            return LdProofBuilder(json)
        }
    }
}
