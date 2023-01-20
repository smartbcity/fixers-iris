package city.smartb.iris.ld.jsonld.serializer

import city.smartb.iris.ld.jsonld.JsonLdObject
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.IOException

class JsonLdObjectSerializer @JvmOverloads constructor(t: Class<JsonLdObject?>? = null) :
    StdSerializer<JsonLdObject>(t) {
    @Throws(IOException::class)
    override fun serialize(value: JsonLdObject, jgen: JsonGenerator, provider: SerializerProvider) {
        jgen.writeObject(value.asJson())
    }
}
