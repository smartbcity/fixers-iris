package city.smartb.iris.ldproof;

import city.smartb.iris.crypto.rsa.signer.Signer;
import city.smartb.iris.jsonld.JsonLdObject;
import city.smartb.iris.ldproof.util.CanonicalizationUtil;
import city.smartb.iris.jsonld.JsonLdConsts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LdProofBuilder {

    public static LdProofBuilder builder() {
        return new LdProofBuilder();
    }

    private final LinkedHashMap<String, Object> json;

    public LdProofBuilder() {
        this.json = new LinkedHashMap<>();
    }

    public LdProofBuilder(Map<String, Object> json) {
        this.json = new LinkedHashMap<>(json);
    }

    public static LdProofBuilder fromLdProof(LdProof ldProof) {
        Map<String, Object> json = ldProof.asJson();
        json.remove(LdProof.JSON_LD_JWS);
        json.remove(LdProof.JSON_LD_SIGNATURE_VALUE);
        return new LdProofBuilder(json);
    }

    public LdProofBuilder withProofPurpose(String proofPurpose) {
        json.put(LdProof.JSON_LD_PURPOSE, proofPurpose);
        return this;
    }

    public LdProofBuilder withCreated(LocalDateTime created) {
        withCreated(created.toString());
        return this;
    }

    public LdProofBuilder withCreated(String created) {
        json.put(LdProof.JSON_LD_CREATED, created);
        return this;
    }

    public LdProofBuilder withVerificationMethod(String verificationMethod) {
        json.put(LdProof.JSON_LD_VERIFICATION_METHOD, verificationMethod);
        return this;
    }

    public LdProofBuilder withChallenge(String challenge) {
        json.put(LdProof.JSON_LD_CHALLENGE, challenge);
        return this;
    }

    public LdProofBuilder withDomain(String domain) {
        json.put(LdProof.JSON_LD_DOMAIN, domain);
        return this;
    }

    public LdProofBuilder withType(String type) {
        json.put(LdProof.JSON_LD_TYPE, type);
        return this;
    }

    public LdProofBuilder withContext(List<String> context) {
        json.put(JsonLdObject.JSON_LD_CONTEXT, context);
        return this;
    }

    public LdProofBuilder withContextDefault() {
        return withContext(Collections.singletonList(LdProof.JSON_LD_CONTEXT_SECURITY_V2));
    }

    public String canonicalize() {
        return CanonicalizationUtil.buildCanonicalizedDocument(json);
    }

    public String canonicalize(Signer signer) {
        json.put(JsonLdObject.JSON_LD_TYPE, signer.getTerm());
        return CanonicalizationUtil.buildCanonicalizedDocument(json);
    }

    private void addSecurityContextToJsonLdObject(LinkedHashMap<String, Object> jsonLdObject) {
        Object context = jsonLdObject.get(JsonLdConsts.CONTEXT);
        List<Object> contexts = getContexts(context);
        if (context instanceof String) {
            contexts.add(context);
        }
        if (!contexts.contains(LdProof.JSON_LD_CONTEXT_SECURITY_V2)) {
            contexts.add(LdProof.JSON_LD_CONTEXT_SECURITY_V2);
        }
        jsonLdObject.put(JsonLdConsts.CONTEXT, contexts);
    }

    private List<Object> getContexts(Object context) {
        if (context instanceof List<?>) {
            return (List<Object>) context;
        }
        return new ArrayList<>();
    }

    public LdProof build(String jws) {
        json.put(LdProof.JSON_LD_JWS, jws);
        return LdProof.fromMap(json);
    }
}
