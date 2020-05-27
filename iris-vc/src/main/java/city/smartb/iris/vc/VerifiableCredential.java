package city.smartb.iris.vc;

import city.smartb.iris.jsonld.JsonLdObject;
import city.smartb.iris.jsonld.reader.JsonField;
import city.smartb.iris.jsonld.reader.JsonFieldReader;
import city.smartb.iris.ldproof.LdProof;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VerifiableCredential extends JsonLdObject {

    public static VerifiableCredential from(LinkedHashMap<String, Object> json) {
        return new VerifiableCredential(json);
    }

    public static final String VC_ISSUER = "issuer";
    public static final String VC_ISSUANCE_DATE = "issuanceDate";
    public static final String VC_CREDENTIAL_SUBJECT = "credentialSubject";

    public VerifiableCredential(Map<String, Object> json) {
        super(json);
    }

    public VerifiableCredential(Map<String, Object> json, JsonFieldReader reader) {
        super(json, reader);
    }

    public String getIssuanceDate() {
        return this.get(VC_ISSUANCE_DATE).asString();
    }

    public String getIssuer() {
        return this.get(VC_ISSUER).asString();
    }

    public <T> T getCredentialSubject(Class<T> clazz) {
        return this.get(VC_CREDENTIAL_SUBJECT).asObject(clazz);
    }

    public <T> List<T> getCredentialSubjectAsList(Class<T> clazz) {
        return this.get(VC_CREDENTIAL_SUBJECT).asListObjects(clazz);
    }

    public <T> JsonField getCredentialSubject() {
        return this.get(VC_CREDENTIAL_SUBJECT);
    }

    public LdProof getProof() {
        Map<String, Object> json = this.get(LdProof.JSON_LD_PROOF).asMap();
        return LdProof.fromMap(json);
    }

}
