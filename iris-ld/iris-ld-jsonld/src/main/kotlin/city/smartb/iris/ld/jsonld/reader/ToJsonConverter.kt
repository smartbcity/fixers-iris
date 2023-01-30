package city.smartb.iris.ld.jsonld.reader

interface ToJsonConverter {
    fun <T> toJson(json: T): Any
    fun <T> toJson(`object`: List<T>?): List<Any>
}
