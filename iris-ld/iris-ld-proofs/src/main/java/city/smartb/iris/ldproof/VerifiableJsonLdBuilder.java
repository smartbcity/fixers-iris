package city.smartb.iris.ldproof;

import city.smartb.iris.jsonld.JsonLdObject;
import city.smartb.iris.ldproof.util.CanonicalizationUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class VerifiableJsonLdBuilder {

    public static VerifiableJsonLdBuilder builder(Map<String, Object> json) {
        return new VerifiableJsonLdBuilder(json);
    }

    private final LinkedHashMap<String, Object> json;

    public VerifiableJsonLdBuilder(Map<String, Object> json) {
        this.json = new LinkedHashMap<>(json);
    }

    public String buildCanonicalizedDocument() {
        removeProofFromJsonLdObject(json);
        return CanonicalizationUtil.buildCanonicalizedDocument(json);
    }

    public static void removeProofFromJsonLdObject(LinkedHashMap<String, Object> json) {
        json.remove(LdProof.JSON_LD_PROOF);
    }

    public VerifiableJsonLd build(LdProof proof) {
        this.json.put(LdProof.JSON_LD_PROOF, proof.asJson());
        return  new VerifiableJsonLd(this.json);
    }
}
