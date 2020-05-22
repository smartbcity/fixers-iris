package city.smartb.iris.ldproof;

import city.smartb.iris.crypto.rsa.signer.Signer;
import city.smartb.iris.jsonld.JsonLdObject;
import city.smartb.iris.ldproof.util.CanonicalizationUtil;
import com.github.jsonldjava.core.JsonLdConsts;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class LdProofBuilder {

    public static LdProofBuilder builder() {
        return new LdProofBuilder();
    }

    private final LinkedHashMap<String, Object> json = new LinkedHashMap<>();

    public LdProofBuilder withProofPurpose(String proofPurpose) {
        json.put(LdProof.JSON_LD_PURPOSE, proofPurpose);
        return this;
    }

    public LdProofBuilder withCreated(Date created) {
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

    public String canonicalize(Signer signer) {
//        addSecurityContextToJsonLdObject(json);
        json.put(JsonLdObject.JSON_TYPE, signer.getTerm());
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
