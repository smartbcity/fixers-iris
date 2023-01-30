package city.smartb.iris.ld.jsonld.serializer

import city.smartb.iris.ld.jsonld.JsonLdObject
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.io.IOException

class JsonLdObjectDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<JsonLdObject>(vc) {
    @Throws(IOException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): JsonLdObject {
        val node = jp.codec.readTree<JsonNode>(jp)
        val mapper = ObjectMapper()
        val result: Map<String, Any> =
            mapper.convertValue<Map<String, Any>>(node, object : TypeReference<Map<String, Any>>() {})
        return JsonLdObject(result)
    }
}
