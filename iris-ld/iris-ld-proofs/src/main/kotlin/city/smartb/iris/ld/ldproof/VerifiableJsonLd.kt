package city.smartb.iris.ld.ldproof

import city.smartb.iris.ld.jsonld.JsonLdObject
import city.smartb.iris.ld.jsonld.reader.JsonFieldReader
import city.smartb.iris.ld.ldproof.serializer.VerifiableJsonLdDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(using = VerifiableJsonLdDeserializer::class)
class VerifiableJsonLd : JsonLdObject {
    constructor(jsonLdObject: Map<String, Any>) : super(jsonLdObject)
    constructor(jsonLdObject: Map<String, Any>, fieldReader: JsonFieldReader) : super(jsonLdObject, fieldReader)

    val proof: LdProof
        get() {
            val json: Map<String, Any> = get(LdProof.JSON_LD_PROOF).asMap()
            return LdProof.fromMap(json)
        }
}
