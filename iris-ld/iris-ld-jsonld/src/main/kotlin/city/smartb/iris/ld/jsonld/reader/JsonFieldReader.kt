package city.smartb.iris.ld.jsonld.reader

interface JsonFieldReader {
    fun read(json: Map<String, Any>, key: String): JsonField
}
