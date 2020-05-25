package city.smartb.iris.jsonld;

import city.smartb.iris.jsonld.reader.JsonField;
import city.smartb.iris.jsonld.reader.JsonFieldJackson;
import city.smartb.iris.jsonld.reader.JsonFieldReader;
import com.github.jsonldjava.core.JsonLdConsts;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonLdObject {

    public static final String JSON_ID = "id";
    public static final String JSON_TYPE = "type";

    protected final LinkedHashMap<String, Object> jsonLdObject;
    private final JsonFieldReader fieldReader;

    public JsonLdObject(Map<String, Object> jsonLdObject) {
        this(jsonLdObject, JsonFieldJackson::new);
    }

    public JsonLdObject(Map<String, Object> jsonLdObject, JsonFieldReader fieldReader) {
        this.jsonLdObject = new LinkedHashMap<>(jsonLdObject);
        this.fieldReader = fieldReader;
    }

    public JsonField get(String key) {
        return fieldReader.read(jsonLdObject, key);
    }

    public List<Object> getContext() {
        return fieldReader.read(jsonLdObject, JsonLdConsts.CONTEXT).asListObjects(Object.class);
    }

    public String getId() {
        String ldId = fieldReader.read(jsonLdObject, JsonLdConsts.ID).asString();
        if(ldId != null) {
            return ldId;
        }
        return fieldReader.read(jsonLdObject, JSON_ID).asString();
    }

    public String getType() {
        String ldId = fieldReader.read(jsonLdObject, JsonLdConsts.TYPE).asString();
        if(ldId != null) {
            return ldId;
        }
        return fieldReader.read(jsonLdObject, JSON_TYPE).asString();
    }

    public Map<String, Object> toJSON() {
        return jsonLdObject;
    }
}
