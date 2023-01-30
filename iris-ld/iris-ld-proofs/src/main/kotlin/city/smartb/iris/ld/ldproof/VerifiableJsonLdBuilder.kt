package city.smartb.iris.ld.ldproof

import city.smartb.iris.ld.ldproof.util.CanonicalizationUtil

class VerifiableJsonLdBuilder(json: Map<String, Any>) {
    private val json: LinkedHashMap<String, Any>

    init {
        this.json = LinkedHashMap(json)
    }

    fun buildCanonicalizedDocument(): String {
        removeProofFromJsonLdObject(json)
        return CanonicalizationUtil.buildCanonicalizedDocument(json)
    }

    fun build(proof: LdProof): VerifiableJsonLd {
        json[LdProof.JSON_LD_PROOF] = proof.asJson()
        return VerifiableJsonLd(json)
    }

    companion object {
        fun builder(json: Map<String, Any>): VerifiableJsonLdBuilder {
            return VerifiableJsonLdBuilder(json)
        }

        fun removeProofFromJsonLdObject(json: LinkedHashMap<String, Any>) {
            json.remove(LdProof.JSON_LD_PROOF)
        }
    }
}
