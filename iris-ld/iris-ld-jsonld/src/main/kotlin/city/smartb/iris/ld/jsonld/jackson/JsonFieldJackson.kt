package city.smartb.iris.ld.jsonld.jackson

import city.smartb.iris.ld.jsonld.reader.JsonField
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.LocalDate
import java.time.LocalDateTime

class JsonFieldJackson(
    private val mapper: ObjectMapper,
    json: Map<String, Any>,
    key: String?,
) : JsonField(json, key) {

    constructor(json: Map<String, Any>, key: String?) : this(ObjectMapperFactory().create(), json, key) {}

    override fun asString(): String? {
        return mapper.convertValue(obj, String::class.java)
    }

    override fun asInteger(): Int {
        return mapper.convertValue(obj, Int::class.java)
    }

    override fun asMap(): Map<String, Any> {
        return mapper.convertValue<Map<String, Any>>(obj, object : TypeReference<Map<String, Any>>() {})
    }

    override val maps: List<Map<String, Any>>
        get() = mapper.convertValue<List<Map<String, Any>>>(
            obj,
            object : TypeReference<List<Map<String, Any>>>() {})

    override fun <T> asObject(clazz: Class<T>?): T {
        return mapper.convertValue(obj, clazz)
    }

    override fun <T> asListObjects(clazz: Class<T>?): List<T> {
        return mapper.convertValue(
            obj, mapper.typeFactory.constructCollectionType(
                MutableList::class.java, clazz
            )
        )
    }

    override fun asLocalDate(): LocalDate {
        return mapper.convertValue(obj, LocalDate::class.java)
    }

    override fun asLocalDateTime(): LocalDateTime {
        return mapper.convertValue(obj, LocalDateTime::class.java)
    }
}
