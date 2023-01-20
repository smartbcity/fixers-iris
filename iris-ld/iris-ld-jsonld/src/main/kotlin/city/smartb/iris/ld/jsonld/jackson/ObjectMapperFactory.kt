package city.smartb.iris.ld.jsonld.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

class ObjectMapperFactory {
    fun create(): ObjectMapper {
        return mapper
    }

    companion object {
        private val mapper: ObjectMapper = JsonMapper.builder()
            .addModule(Jdk8Module())
            .addModule(JavaTimeModule())
            .build()

    }
}
