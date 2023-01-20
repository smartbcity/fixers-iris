//package city.smartb.iris.ld.jsonld
//
//import com.apicatalog.jsonld.uri.UriResolver
//import city.smartb.iris.ld.jsonld.exception.JsonLdDereferencingException
//import java.net.URI
//import java.net.URISyntaxException
//import java.util.function.Predicate
//
//object JsonLdDereferencer {
//
//    fun findByIdInJsonLdObject(jsonLdObject: JsonLdObject, uri: URI, baseUri: URI?): JsonLdObject? {
//        if (jsonLdObject.id != null) {
//            require(!(!uri.isAbsolute && baseUri == null)) { "No base URI for relative uri $uri" }
//            val findId = URI.create(UriResolver.resolve(baseUri, uri.toString()))
//            var idUri = URI.create(jsonLdObject.id)
//            require(!(!idUri.isAbsolute && baseUri == null)) { "No base URI for relative 'id' uri $uri" }
//            idUri = URI.create(UriResolver.resolve(baseUri, idUri.toString()))
//            if (findId == idUri) {
//                return jsonLdObject
//            }
//        }
//        val var6: Iterator<*> = jsonLdObject.asJson().values.iterator()
//        while (var6.hasNext()) {
//            val value = var6.next()!!
//            var foundJsonLdObject: JsonLdObject?
//            if (value is Map<String, Any>) {
//                foundJsonLdObject = findByIdInJsonLdObject(JsonLdObject(value), uri, baseUri)
//                if (foundJsonLdObject != null) {
//                    return foundJsonLdObject
//                }
//            } else if (value is List<*>) {
//                foundJsonLdObject = findByIdInList(value, uri, baseUri)
//                if (foundJsonLdObject != null) {
//                    return foundJsonLdObject
//                }
//            }
//        }
//        return null
//    }
//
//    private fun findByIdInList(list: List<Any>, uri: URI, baseUri: URI?): JsonLdObject? {
//        val var3: Iterator<*> = list.iterator()
//        while (var3.hasNext()) {
//            val value = var3.next()!!
//            var foundJsonLdObject: JsonLdObject?
//            if (value is Map<String, Any>) {
//                foundJsonLdObject = findByIdInJsonLdObject(JsonLdObject(value), uri, baseUri)
//                if (foundJsonLdObject != null) {
//                    return foundJsonLdObject
//                }
//            } else if (value is List<*>) {
//                foundJsonLdObject = findByIdInList(value, uri, baseUri)
//                if (foundJsonLdObject != null) {
//                    return foundJsonLdObject
//                }
//            }
//        }
//        return null
//    }
//
//    class Function : java.util.function.Function<Any, JsonLdObject> {
//        private var jsonLdDocument: JsonLdObject
//        private var baseUri: URI?
//        private var predicate: Predicate<JsonLdObject>?
//
//        constructor(jsonLdDocument: JsonLdObject, baseUri: URI?, predicate: Predicate<JsonLdObject>?) {
//            this.jsonLdDocument = jsonLdDocument
//            this.baseUri = baseUri
//            this.predicate = predicate
//        }
//
//        constructor(jsonLdDocument: JsonLdObject, baseUri: URI?) {
//            this.jsonLdDocument = jsonLdDocument
//            this.baseUri = baseUri
//            predicate = null
//        }
//
//        constructor(jsonLdDocument: JsonLdObject) {
//            this.jsonLdDocument = jsonLdDocument
//            baseUri = null
//            predicate = null
//        }
//
//        @Throws(JsonLdDereferencingException::class)
//        override fun apply(o: Any): JsonLdObject {
//            var uri: URI? = null
//            var result: JsonLdObject? = null
//            if (o is JsonLdObject) {
//                result = o
//            } else if (o is Map<*, *>) {
//                result = JsonLdObject(o)
//            } else {
//                if (o !is String) {
//                    throw JsonLdDereferencingException("Cannot dereference non-URI value: $o")
//                }
//                uri = try {
//                    URI(o)
//                } catch (var5: URISyntaxException) {
//                    throw JsonLdDereferencingException("Cannot dereference non-URI string: $o")
//                }
//                result = findByIdInJsonLdObject(jsonLdDocument, uri, baseUri)
//            }
//            if (result != null && predicate != null) {
//                val test = predicate!!.test(result)
//                if (!test) {
//                    throw JsonLdDereferencingException("Unacceptable result for dereferencing URI $uri")
//                }
//            }
//            return result ?: throw JsonLdDereferencingException("No result for dereferencing URI $uri")
//        }
//    }
//}
