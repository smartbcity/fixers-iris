package city.smartb.iris.jsonld;

import city.smartb.iris.jsonld.exception.JsonLdDereferencingException;
import com.apicatalog.jsonld.uri.UriResolver;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class JsonLdDereferencer {
    public JsonLdDereferencer() {
    }

    public static JsonLdObject findByIdInJsonLdObject(JsonLdObject jsonLdObject, URI uri, URI baseUri) {
        if (jsonLdObject.getId() != null) {
            if (!uri.isAbsolute() && baseUri == null) {
                throw new IllegalArgumentException("No base URI for relative uri " + uri);
            }

            URI findId = URI.create(UriResolver.resolve(baseUri, uri.toString()));
            URI idUri = URI.create(jsonLdObject.getId());
            if (!idUri.isAbsolute() && baseUri == null) {
                throw new IllegalArgumentException("No base URI for relative 'id' uri " + uri);
            }

            idUri = URI.create(UriResolver.resolve(baseUri, idUri.toString()));
            if (findId.equals(idUri)) {
                return jsonLdObject;
            }
        }

        Iterator var6 = jsonLdObject.asJson().values().iterator();

        while(var6.hasNext()) {
            Object value = var6.next();
            JsonLdObject foundJsonLdObject;
            if (value instanceof Map) {
                foundJsonLdObject = findByIdInJsonLdObject(new JsonLdObject((Map)value), uri, baseUri);
                if (foundJsonLdObject != null) {
                    return foundJsonLdObject;
                }
            } else if (value instanceof List) {
                foundJsonLdObject = findByIdInList((List)value, uri, baseUri);
                if (foundJsonLdObject != null) {
                    return foundJsonLdObject;
                }
            }
        }

        return null;
    }

    private static JsonLdObject findByIdInList(List<Object> list, URI uri, URI baseUri) {
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            Object value = var3.next();
            JsonLdObject foundJsonLdObject;
            if (value instanceof Map) {
                foundJsonLdObject = findByIdInJsonLdObject(new JsonLdObject((Map)value), uri, baseUri);
                if (foundJsonLdObject != null) {
                    return foundJsonLdObject;
                }
            } else if (value instanceof List) {
                foundJsonLdObject = findByIdInList((List)value, uri, baseUri);
                if (foundJsonLdObject != null) {
                    return foundJsonLdObject;
                }
            }
        }

        return null;
    }

    public static class Function implements java.util.function.Function<Object, JsonLdObject> {
        private JsonLdObject jsonLdDocument;
        private URI baseUri;
        private Predicate<JsonLdObject> predicate;

        public Function(JsonLdObject jsonLdDocument, URI baseUri, Predicate<JsonLdObject> predicate) {
            this.jsonLdDocument = jsonLdDocument;
            this.baseUri = baseUri;
            this.predicate = predicate;
        }

        public Function(JsonLdObject jsonLdDocument, URI baseUri) {
            this.jsonLdDocument = jsonLdDocument;
            this.baseUri = baseUri;
            this.predicate = null;
        }

        public Function(JsonLdObject jsonLdDocument) {
            this.jsonLdDocument = jsonLdDocument;
            this.baseUri = null;
            this.predicate = null;
        }

        public JsonLdObject apply(Object o) throws JsonLdDereferencingException {
            URI uri = null;
            JsonLdObject result = null;
            if (o instanceof JsonLdObject) {
                result = (JsonLdObject)o;
            } else if (o instanceof Map) {
                result = new JsonLdObject((Map)o);
            } else {
                if (!(o instanceof String)) {
                    throw new JsonLdDereferencingException("Cannot dereference non-URI value: " + o);
                }

                try {
                    uri = new URI((String)o);
                } catch (URISyntaxException var5) {
                    throw new JsonLdDereferencingException("Cannot dereference non-URI string: " + o);
                }

                result = JsonLdDereferencer.findByIdInJsonLdObject(this.jsonLdDocument, uri, this.baseUri);
            }

            if (result != null && this.predicate != null) {
                boolean test = this.predicate.test(result);
                if (!test) {
                    throw new JsonLdDereferencingException("Unacceptable result for dereferencing URI " + uri);
                }
            }

            if (result == null) {
                throw new JsonLdDereferencingException("No result for dereferencing URI " + uri);
            } else {
                return result;
            }
        }
    }
}
