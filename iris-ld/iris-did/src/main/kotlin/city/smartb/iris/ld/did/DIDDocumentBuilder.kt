package city.smartb.iris.ld.did

import city.smartb.iris.ld.jsonld.JsonLdObject
import city.smartb.iris.ld.jsonld.reader.JsonFieldReader
import city.smartb.iris.ld.ldproof.LdProof
import java.util.stream.Collectors

class DIDDocumentBuilder : JsonLdObject {
    constructor(json: Map<String, Any>) : super(json) {}
    constructor(json: Map<String, Any>, reader: JsonFieldReader) : super(json, reader) {}

    companion object {
        fun create(): DIDDocumentBuilder {
            return DIDDocumentBuilder(LinkedHashMap())
        }

        fun fromMap(json: LinkedHashMap<String, Any>): DIDDocumentBuilder {
            return DIDDocumentBuilder(json)
        }
    }

    fun withId(id: String): DIDDocumentBuilder {
        jsonLdObject[JSON_LD_ID] = id
        return this
    }

    fun withVerificationMethods(methods: List<DIDVerificationMethod>): DIDDocumentBuilder {
        val methodsJson = methods.parallelStream().map { method: DIDVerificationMethod -> method.asJson() }
            .collect(Collectors.toList())
        jsonLdObject[DIDDocument.JSON_LD_VERIFICATION_METHOD] = methodsJson
        return this
    }

    fun withAssertionMethods(items: List<String>): DIDDocumentBuilder {
        jsonLdObject[DIDDocument.JSON_LD_ASSERTION_METHOD] = items
        return this
    }

    fun withCapabilityInvocations(items: List<String>): DIDDocumentBuilder {
        jsonLdObject[DIDDocument.JSON_LD_CAPABILITY_INVOCATION] = items
        return this
    }

    fun withCapabilityDelegations(items: List<String>): DIDDocumentBuilder {
        jsonLdObject[DIDDocument.JSON_LD_CAPABILITY_DELEGATION] = items
        return this
    }

    fun withKeyAgreements(items: List<String>): DIDDocumentBuilder {
        jsonLdObject[DIDDocument.JSON_LD_KEY_AGREEMENT] = items
        return this
    }

    fun withVerificationMethod(method: DIDVerificationMethod): DIDDocumentBuilder {
        val methods = listOf(method)
        return withVerificationMethods(methods)
    }

    fun withServices(services: List<DIDService>): DIDDocumentBuilder {
        val servicesJSon = services.parallelStream().map { service: DIDService -> service.asJson() }
            .collect(Collectors.toList())
        jsonLdObject[DIDDocument.JSON_LD_SERVICE] = servicesJSon
        return this
    }

    fun withService(service: DIDService): DIDDocumentBuilder {
        val services = listOf(service)
        return withServices(services)
    }

    fun withAuthentications(authentications: List<DIDAuthentication>): DIDDocumentBuilder {
        val authenticationsJson =
            authentications.parallelStream().map { authentication: DIDAuthentication -> authentication.toJSON() }
                .collect(Collectors.toList())
        jsonLdObject[DIDDocument.JSON_LD_AUTHENTICATION] = authenticationsJson
        return this
    }

    fun withAuthentication(authentication: DIDAuthentication): DIDDocumentBuilder {
        val authentications = listOf(authentication)
        return withAuthentications(authentications)
    }

    fun withController(controller: String): DIDDocumentBuilder {
        jsonLdObject[ControledJsonLdObject.JSON_LD_CONTROLER] = controller
        return this
    }

    val proof: List<DIDAuthentication>
        get() = this[DIDDocument.JSON_LD_AUTHENTICATION]
            .asListObjects(Any::class.java)
            .stream()
            .map { DIDAuthentication(it)
            }
            .collect(Collectors.toList())

    override fun asJson(): Map<String, Any> {
        return jsonLdObject
    }

    fun asJson(proof: LdProof): DIDDocument {
        jsonLdObject[LdProof.JSON_LD_PROOF] = proof
        return DIDDocument(jsonLdObject)
    }
}
