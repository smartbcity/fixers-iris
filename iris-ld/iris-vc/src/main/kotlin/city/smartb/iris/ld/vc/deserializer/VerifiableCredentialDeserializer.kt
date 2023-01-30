package vc.deserializer

import city.smartb.iris.ld.vc.VerifiableCredential
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.io.IOException

class VerifiableCredentialDeserializer @JvmOverloads constructor(vc: Class<*>? = null) :
    StdDeserializer<VerifiableCredential>(vc) {
    @Throws(IOException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): VerifiableCredential {
        val node = jp.codec.readTree<JsonNode>(jp)
        val mapper = ObjectMapper()
        val result: Map<String, Any> =
            mapper.convertValue<Map<String, Any>>(node, object : TypeReference<Map<String, Any>>() {})
        return VerifiableCredential(result)
    }
}
