package city.smartb.iris.ldproof;

import city.smartb.iris.jsonld.JsonLdObject;
import city.smartb.iris.jsonld.reader.JsonFieldReader;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class LdProof extends JsonLdObject {

    public static LdProof fromMap(Map<String, Object> json) {
        return new LdProof(json);
    }

    public static final String JSON_LD_CONTEXT_SECURITY_V2 = "https://w3id.org/security/v2";

    public static final String JSON_LD_PROOF = "proof";

    public static final String JSON_LD_CREATED = "created";
    public static final String JSON_LD_DOMAIN = "domain";
    public static final String JSON_LD_CHALLENGE = "challenge";
    public static final String JSON_LD_PURPOSE = "proofPurpose";
    public static final String JSON_LD_VERIFICATION_METHOD = "verificationMethod";
    public static final String JSON_LD_SIGNATURE_VALUE = "signatureValue";
    public static final String JSON_LD_JWS = "jws";

    public LdProof(Map<String, Object> jsonLdObject) {
        super(jsonLdObject);
    }

    public LdProof(Map<String, Object> jsonLdObject, JsonFieldReader fieldReader) {
        super(jsonLdObject, fieldReader);
    }

    public String getProofPurpose() {
        return get(JSON_LD_PURPOSE).asString();
    }

    public LocalDateTime getCreated() {
        return get(JSON_LD_CREATED).asLocalDateTime();
    }

    public String getVerificationMethod() {
        return get(JSON_LD_VERIFICATION_METHOD).asString();
    }

    public String getChallenge() {
        return get(JSON_LD_CHALLENGE).asString();
    }

    public String getDomain() {
        return get(JSON_LD_DOMAIN).asString();
    }

    public String getType() {
        return get(JSON_TYPE).asString();
    }

    public String getJws() {
        return get(JSON_LD_JWS).asString();
    }

    public LinkedHashMap<String, Object> asJson() {
        return new LinkedHashMap<>(this.jsonLdObject);
    }

}
