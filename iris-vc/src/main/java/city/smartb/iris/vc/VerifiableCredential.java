package city.smartb.iris.vc;

import city.smartb.iris.jsonld.JsonLdObject;
import city.smartb.iris.jsonld.reader.JsonFieldReader;
import city.smartb.iris.ldproof.LdProof;

import java.util.LinkedHashMap;
import java.util.Map;

public class VerifiableCredential extends JsonLdObject {

    public static VerifiableCredential create() {
        return new VerifiableCredential(new LinkedHashMap<>());
    }

    public static VerifiableCredential from(LinkedHashMap<String, Object> json) {
        return new VerifiableCredential(json);
    }

    public static final String JSON_LD_ISSUER = "issuer";
    public static final String JSON_LD_ISSUANCE_DATE = "issuanceDate";
    public static final String JSON_LD_CREDENTIAL_SUBJECT = "credentialSubject";

    public VerifiableCredential(Map<String, Object> json) {
        super(json);
    }

    public VerifiableCredential(Map<String, Object> json, JsonFieldReader reader) {
        super(json, reader);
    }

    public String getIssuanceDate() {
        return this.get(JSON_LD_ISSUANCE_DATE).asString();
    }

    public String getIssuer() {
        return this.get(JSON_LD_ISSUER).asString();
    }

    public Map<String, Object> getCredentialSubject() {
        return this.get(JSON_LD_CREDENTIAL_SUBJECT).asMap();
    }

    public LdProof getProof() {
        Map<String, Object> map = this.get(LdProof.JSON_LD_PROOF).asMap();
        return LdProof.fromMap(map);
    }

}
