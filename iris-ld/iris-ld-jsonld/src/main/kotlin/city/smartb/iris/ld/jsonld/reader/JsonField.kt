package city.smartb.iris.ld.jsonld.reader

import java.time.LocalDate
import java.time.LocalDateTime

abstract class JsonField {
    protected val obj: Any?

    constructor(obj: Any) {
        this.obj = obj
    }

    constructor(json: Map<String, Any>, key: String?) {
        obj = json[key]
    }

    abstract fun asString(): String?
    abstract fun asInteger(): Int
    abstract fun asMap(): Map<String, Any>
    abstract val maps: List<Map<String, Any>>
    abstract fun <T> asObject(clazz: Class<T>?): T
    abstract fun <T> asListObjects(clazz: Class<T>?): List<T>
    abstract fun asLocalDate(): LocalDate
    abstract fun asLocalDateTime(): LocalDateTime
}
