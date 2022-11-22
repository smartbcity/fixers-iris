package city.smartb.iris.resolver.utils

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

fun <T> String.parseTo(targetClass: Class<T>): T {
    val mapper = ObjectMapper()
        .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerKotlinModule()

    return mapper.readValue(this, targetClass)
}
