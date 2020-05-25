package city.smartb.iris.ldproof;

import city.smartb.iris.jsonld.JsonLdObject;
import city.smartb.iris.jsonld.reader.JsonFieldReader;

import java.util.Map;

public class LdJsonObject extends JsonLdObject {

    public LdJsonObject(Map<String, Object> jsonLdObject) {
        super(jsonLdObject);
    }

    public LdJsonObject(Map<String, Object> jsonLdObject, JsonFieldReader fieldReader) {
        super(jsonLdObject, fieldReader);
    }

    public LdProof getProof() {
        Map<String, Object> json = get(LdProof.JSON_LD_PROOF).asMap();
        return LdProof.fromMap(json);
    }

}
