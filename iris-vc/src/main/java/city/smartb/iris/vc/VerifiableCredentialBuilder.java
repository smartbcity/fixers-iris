package city.smartb.iris.vc;

import city.smartb.iris.jsonld.JsonLdObject;
import city.smartb.iris.ldproof.LdProof;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VerifiableCredentialBuilder extends JsonLdObject {

    public static VerifiableCredentialBuilder create() {
        return new VerifiableCredentialBuilder(new LinkedHashMap<>());
    }

    public static VerifiableCredentialBuilder fromMap(LinkedHashMap<String, Object> json) {
        return new VerifiableCredentialBuilder(json);
    }

    public static final String VC_CONTEXT = "@context";
    public static final String VC_ID = "id";
    public static final String VC_TYPE = "type";
    public static final String VC_ISSUER = "issuer";
    public static final String VC_ISSUANCE_DATE = "issuanceDate";
    public static final String VC_CREDENTIAL_SUBJECT = "credentialSubject";
    public static final String VC_PROOF = "proof";

    public VerifiableCredentialBuilder(Map<String, Object> jsonLdObject) {
        super(jsonLdObject);
    }

    public VerifiableCredentialBuilder withId(String id) {
        this.jsonLdObject.put(VC_ID, id);
        return this;
    }

    public VerifiableCredentialBuilder withType(String type) {
        this.jsonLdObject.put(VC_TYPE, type);
        return this;
    }

    public VerifiableCredentialBuilder withContext(List<String> context) {
        this.jsonLdObject.put(VC_CONTEXT, context);
        return this;
    }
    public VerifiableCredentialBuilder withIssuanceDate(String issuanceDate) {
        this.jsonLdObject.put(VC_ISSUANCE_DATE, issuanceDate);
        return this;
    }

    public VerifiableCredentialBuilder withIssuer(String issuer) {
        this.jsonLdObject.put(VC_ISSUER, issuer);
        return this;
    }

    public VerifiableCredentialBuilder withCredentialSubject(Map<String, Object> subject) {
        this.jsonLdObject.put(VC_CREDENTIAL_SUBJECT, subject);
        return this;
    }

    public LinkedHashMap<String, Object> asJson() {
        return new LinkedHashMap<>(jsonLdObject);
    }

    public VerifiableCredential asJson(LdProof proof) {
        this.jsonLdObject.put(VC_PROOF, proof);
        return  new VerifiableCredential(this.jsonLdObject);
    }
}
