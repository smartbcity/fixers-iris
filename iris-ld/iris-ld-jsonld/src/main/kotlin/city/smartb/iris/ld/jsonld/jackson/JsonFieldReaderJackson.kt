package city.smartb.iris.ld.jsonld.jackson

import city.smartb.iris.ld.jsonld.reader.JsonField
import city.smartb.iris.ld.jsonld.reader.JsonFieldReader
import com.fasterxml.jackson.databind.ObjectMapper

class JsonFieldReaderJackson @JvmOverloads constructor(
    private val mapper: ObjectMapper = ObjectMapperFactory().create()
) : JsonFieldReader {

    override fun read(json: Map<String, Any>, key: String): JsonField {
        return JsonFieldJackson(mapper, json, key)
    }

}
