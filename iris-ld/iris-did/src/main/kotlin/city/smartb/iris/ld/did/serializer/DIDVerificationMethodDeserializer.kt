package city.smartb.iris.ld.did.serializer//package city.smartb.iris.did.model.serializer

import city.smartb.iris.ld.did.DIDVerificationMethod
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.io.IOException

class DIDVerificationMethodDeserializer @JvmOverloads constructor(vc: Class<*>? = null) :
    StdDeserializer<DIDVerificationMethod?>(vc) {
    @Throws(IOException::class)

    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): DIDVerificationMethod {
        val node = jp.codec.readTree<JsonNode>(jp)
        val mapper = ObjectMapper()
        val result: Map<String, Any> =
            mapper.convertValue<Map<String, Any>>(node, object : TypeReference<Map<String, Any>>() {})

        return DIDVerificationMethod(result)
    }
}
