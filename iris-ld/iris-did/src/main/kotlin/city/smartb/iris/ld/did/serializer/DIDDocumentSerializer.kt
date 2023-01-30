package city.smartb.iris.ld.did.serializer

import city.smartb.iris.ld.did.DIDDocument
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.IOException

class DIDDocumentSerializer @JvmOverloads constructor(t: Class<DIDDocument?>? = null) :
    StdSerializer<DIDDocument?>(t) {

    @Throws(IOException::class)
    override fun serialize(value: DIDDocument?, gen: JsonGenerator?, provider: SerializerProvider?) {
        gen!!.writeObject(value!!.asJson())
    }
}
