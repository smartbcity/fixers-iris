package city.smartb.iris.ld.did

import city.smartb.iris.ld.jsonld.JsonLdObject
import city.smartb.iris.ld.jsonld.reader.JsonFieldReader

class DIDService : JsonLdObject {
    constructor(json: Map<String, Any>) : super(json)
    constructor(json: Map<String, Any>, reader: JsonFieldReader) : super(json, reader)

    val serviceEndpoint: String?
        get() = this[JSON_LD_SERVICE_ENDPOINT].asString()

    companion object {
        const val JSON_LD_SERVICE_ENDPOINT = "serviceEndpoint"
    }
}
