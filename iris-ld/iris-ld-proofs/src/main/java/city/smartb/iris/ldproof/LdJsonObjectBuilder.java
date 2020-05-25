package city.smartb.iris.ldproof;

import city.smartb.iris.ldproof.util.CanonicalizationUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class LdJsonObjectBuilder {

    public static LdJsonObjectBuilder builder(Map<String, Object> json) {
        return new LdJsonObjectBuilder(json);
    }

    private final LinkedHashMap<String, Object> json;

    public LdJsonObjectBuilder(Map<String, Object> json) {
        this.json = new LinkedHashMap<>(json);
    }

    public String buildCanonicalizedDocument() {
        removeFromJsonLdObject(json);
        return CanonicalizationUtil.buildCanonicalizedDocument(json);
    }

    public static void removeFromJsonLdObject(LinkedHashMap<String, Object> jsonLdObject) {
        jsonLdObject.remove(LdProof.JSON_LD_PROOF);
    }

}
