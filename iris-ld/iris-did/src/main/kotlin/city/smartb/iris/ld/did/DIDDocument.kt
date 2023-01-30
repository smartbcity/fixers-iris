package city.smartb.iris.ld.did

import city.smartb.iris.ld.did.serializer.DIDDocumentDeserializer
import city.smartb.iris.ld.did.serializer.DIDDocumentSerializer
import city.smartb.iris.ld.jsonld.reader.JsonFieldReader
import city.smartb.iris.ld.ldproof.LdProof
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.util.stream.Collectors

@JsonDeserialize(using = DIDDocumentDeserializer::class)
@JsonSerialize(using = DIDDocumentSerializer::class)
class DIDDocument : ControledJsonLdObject {
    constructor() : super(LinkedHashMap<String, Any>()) {}
    constructor(json: Map<String, Any>) : super(json) {}
    constructor(json: Map<String, Any>, reader: JsonFieldReader) : super(json, reader) {}

    companion object {
        const val MIME_TYPE = "application/did+ld+json"
        const val JSON_LD_SERVICE = "service"
        const val JSON_LD_AUTHENTICATION = "authentication"
        const val JSON_LD_VERIFICATION_METHOD = "verificationMethod"
        const val JSON_LD_ASSERTION_METHOD = "assertionMethod"
        const val JSON_LD_CAPABILITY_INVOCATION = "capabilityInvocation"
        const val JSON_LD_CAPABILITY_DELEGATION = "capabilityDelegation"
        const val JSON_LD_KEY_AGREEMENT = "keyAgreement"
    }

    val proof: LdProof
        get() {
//            val map = this.jsonLdObject[LdProof.JSON_LD_PROOF]
//            return if (map == null) null else LdProof.fromMap(map as Map<String, Any>)
            val map = this[LdProof.JSON_LD_PROOF].asMap()
            return LdProof.fromMap(map)
        }

    val verificationMethod: MutableList<DIDVerificationMethod>
        get() {
            val methods = this[JSON_LD_VERIFICATION_METHOD]
                .asListObjects(DIDVerificationMethod::class.java) as MutableList
            return methods
        }

    fun addVerificationMethod(method: DIDVerificationMethod): DIDDocument {
        val list = verificationMethod
        list.add(method)
        jsonLdObject[JSON_LD_VERIFICATION_METHOD] = list
        return this
    }

    fun addAssertionMethod(keyId: String): DIDDocument {
        val list = assertionMethod
        list.add(keyId)
        jsonLdObject[JSON_LD_ASSERTION_METHOD] = list
        return this
    }

    fun addCapabilityInvocation(keyId: String): DIDDocument {
        val list = capabilityInvocation
        list.add(keyId)
        jsonLdObject[JSON_LD_CAPABILITY_INVOCATION] = list
        return this
    }

    fun addCapabilityDelegation(keyId: String): DIDDocument {
        val list = capabilityDelegation
        list.add(keyId)
        jsonLdObject[JSON_LD_CAPABILITY_DELEGATION] = list
        return this
    }

    fun addKeyAgreement(keyId: String): DIDDocument {
        val list = keyAgreement
        list.add(keyId)
        jsonLdObject[JSON_LD_KEY_AGREEMENT] = list
        return this
    }

    fun addAuthentication(keyId: String): DIDDocument {
        val list = authentication
        list.add(keyId)
        jsonLdObject[JSON_LD_AUTHENTICATION] = list
        return this
    }

    fun removeVerificationMethod(id: String): DIDDocument {
        val list = this[JSON_LD_VERIFICATION_METHOD]
            .asListObjects(DIDVerificationMethod::class.java) as MutableList
        val item = list
            .stream()
            .filter { it: DIDVerificationMethod -> (it.id == id) }
            .collect(Collectors.toList())[0]
        list.remove(item)
        jsonLdObject[JSON_LD_VERIFICATION_METHOD] = list
        return this
    }

    val service: MutableList<DIDService>
        get() = this.get(JSON_LD_SERVICE).asListObjects(DIDService::class.java) as MutableList

    val authentication: MutableList<String>
        get() = this.get(JSON_LD_AUTHENTICATION).asListObjects(String::class.java) as MutableList

    val assertionMethod: MutableList<String>
        get() = this.get(JSON_LD_ASSERTION_METHOD).asListObjects(String::class.java) as MutableList

    val capabilityInvocation: MutableList<String>
        get() = try {
            this[JSON_LD_CAPABILITY_INVOCATION]
                .asListObjects(String::class.java) as MutableList
        } catch (e: NullPointerException) {
            ArrayList()
        }

    val capabilityDelegation: MutableList<String>
        get() {
            return try {
                this[JSON_LD_CAPABILITY_DELEGATION]
                    .asListObjects(String::class.java) as MutableList
            } catch (e: NullPointerException) {
                ArrayList()
            }
        }

    val keyAgreement: MutableList<String>
        get() {
            return try {
                this[JSON_LD_KEY_AGREEMENT]
                    .asListObjects(String::class.java) as MutableList
            } catch (e: NullPointerException) {
                ArrayList()
            }
        }

    fun setProof(proof: LdProof): DIDDocument {
        jsonLdObject.remove(LdProof.JSON_LD_PROOF)
        jsonLdObject[LdProof.JSON_LD_PROOF] = proof
        return this
    }
}
