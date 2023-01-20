package city.smartb.iris.ld.jsonld

import city.smartb.iris.ld.jsonld.jackson.JsonFieldReaderJackson
import city.smartb.iris.ld.jsonld.reader.JsonField
import city.smartb.iris.ld.jsonld.reader.JsonFieldReader
import city.smartb.iris.ld.jsonld.serializer.JsonLdObjectDeserializer
import city.smartb.iris.ld.jsonld.serializer.JsonLdObjectSerializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize(using = JsonLdObjectSerializer::class)
@JsonDeserialize(using = JsonLdObjectDeserializer::class)
open class JsonLdObject {
    protected var jsonLdObject: LinkedHashMap<String, Any>
    private var fieldReader: JsonFieldReader

    companion object {
        const val JSON_LD_CONTEXT = "@context"
        const val JSON_LD_ID = "id"
        const val JSON_LD_TYPE = "type"
    }

    constructor(jsonLdObject: Map<String, Any>) {
        this.jsonLdObject = LinkedHashMap(jsonLdObject)
        this.fieldReader = JsonFieldReaderJackson()
    }

    constructor(jsonLdObject: Map<String, Any>, fieldReader: JsonFieldReader) {
        this.jsonLdObject = LinkedHashMap(jsonLdObject)
        this.fieldReader = fieldReader
    }

    operator fun get(key: String): JsonField {
        return fieldReader.read(jsonLdObject, key)
    }

    val context: List<Any>
        get() = fieldReader.read(jsonLdObject, JsonLdConsts.CONTEXT).asListObjects(Any::class.java)

    val id: String
        get() {
            val ldId = fieldReader.read(jsonLdObject, JSON_LD_ID).asString()
            return ldId ?: fieldReader.read(jsonLdObject, JSON_LD_ID).asString()
        }
    val type: String
        get() {
            val ldId = fieldReader.read(jsonLdObject, JsonLdConsts.TYPE).asString()
            return ldId ?: fieldReader.read(jsonLdObject, JSON_LD_TYPE).asString()
        }

    open fun asJson(): Map<String, Any> {
        return jsonLdObject
    }
}
