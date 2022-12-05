package city.smartb.iris.ldproof;

import city.smartb.iris.jsonld.JsonLdObject;
import city.smartb.iris.jsonld.reader.JsonFieldReader;
import city.smartb.iris.ldproof.serializer.VerifiableJsonLdDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Map;

@JsonDeserialize(using = VerifiableJsonLdDeserializer.class)
public class VerifiableJsonLd extends JsonLdObject {

    public VerifiableJsonLd(Map<String, Object> jsonLdObject) {
        super(jsonLdObject);
    }

    public VerifiableJsonLd(Map<String, Object> jsonLdObject, JsonFieldReader fieldReader) {
        super(jsonLdObject, fieldReader);
    }

    public LdProof getProof() {
        Map<String, Object> json = get(LdProof.JSON_LD_PROOF).asMap();
        return LdProof.fromMap(json);
    }
}
