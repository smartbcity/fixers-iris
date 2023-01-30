package city.smartb.iris.ld.jsonld.jackson

import city.smartb.iris.ld.jsonld.reader.ToJsonConverter
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

class ToToJsonConvertorJackson @JvmOverloads constructor(private val mapper: ObjectMapper = ObjectMapperFactory().create()) :
    ToJsonConverter {
    override fun <T> toJson(`object`: T): Any {
        return mapper!!.convertValue(`object`, object : TypeReference<Any>() {})
    }

    override fun <T> toJson(`object`: List<T>?): List<Any> {
        return mapper.convertValue<List<Any>>(`object`, object : TypeReference<List<Any>>() {})
    }
}
