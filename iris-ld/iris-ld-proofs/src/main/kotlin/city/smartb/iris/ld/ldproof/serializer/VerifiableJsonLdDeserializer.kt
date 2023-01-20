package city.smartb.iris.ld.ldproof.serializer

import city.smartb.iris.ld.ldproof.VerifiableJsonLd
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.io.IOException

class VerifiableJsonLdDeserializer @JvmOverloads constructor(vc: Class<*>? = null) :
    StdDeserializer<VerifiableJsonLd>(vc) {
    @Throws(IOException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): VerifiableJsonLd {
        val node = jp.codec.readTree<JsonNode>(jp)
        val mapper = ObjectMapper()
        val result = mapper.convertValue(node, object : TypeReference<Map<String, Any>>() {})
        return VerifiableJsonLd(result)
    }
}
