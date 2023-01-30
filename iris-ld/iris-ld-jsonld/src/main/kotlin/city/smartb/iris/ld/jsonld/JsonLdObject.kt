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
    protected var jsonLd: LinkedHashMap<String, Any>
    private var fieldReader: JsonFieldReader

    companion object {
        const val JSON_LD_CONTEXT = "@context"
        const val JSON_LD_ID = "id"
        const val JSON_LD_TYPE = "type"
    }

    constructor(jsonLdObject: Map<String, Any>) {
        this.jsonLd = LinkedHashMap(jsonLdObject)
        this.fieldReader = JsonFieldReaderJackson()
    }

    constructor(jsonLdObject: Map<String, Any>, fieldReader: JsonFieldReader) {
        this.jsonLd = LinkedHashMap(jsonLdObject)
        this.fieldReader = fieldReader
    }

    operator fun get(key: String): JsonField {
        return fieldReader.read(jsonLd, key)
    }

    val context: List<Any>
        get() = fieldReader.read(jsonLd, JsonLdConsts.CONTEXT).asListObjects(Any::class.java)

    open val id: String?
        get() {
            val ldId = fieldReader.read(jsonLd, JSON_LD_ID).asString()
            return ldId
        }
    val type: String?
        get() {
            val ldId = fieldReader.read(jsonLd, JsonLdConsts.TYPE).asString()
            return ldId ?: fieldReader.read(jsonLd, JSON_LD_TYPE).asString()
        }

    open fun asJson(): Map<String, Any> {
        return jsonLd
    }
}
