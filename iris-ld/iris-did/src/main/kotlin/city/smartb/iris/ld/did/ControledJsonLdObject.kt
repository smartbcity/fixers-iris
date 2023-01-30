package city.smartb.iris.ld.did

import city.smartb.iris.ld.jsonld.JsonLdObject
import city.smartb.iris.ld.jsonld.reader.JsonFieldReader

abstract class ControledJsonLdObject : JsonLdObject {
    constructor(jsonLdObject: Map<String, Any>) : super(jsonLdObject) {}
    constructor(jsonLdObject: Map<String, Any>, fieldReader: JsonFieldReader) : super(jsonLdObject, fieldReader) {}

    companion object {
        const val JSON_LD_CONTROLER = "controller"
    }

    val controller: String?
        get() = this[JSON_LD_CONTROLER].asString()
}
