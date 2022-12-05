package city.smartb.iris.ldproof.verifier;

import city.smartb.iris.crypto.dsl.verifier.Verifier;
import city.smartb.iris.ldproof.VerifiableJsonLd;
import city.smartb.iris.ldproof.VerifiableJsonLdBuilder;
import city.smartb.iris.ldproof.LdProof;
import city.smartb.iris.ldproof.LdProofBuilder;
import city.smartb.iris.ldproof.util.JWSUtil;
import city.smartb.iris.ldproof.util.SHAUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;

import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.Map;

public abstract class LdProofVerifier {

    private Verifier verifier;

    protected LdProofVerifier(Verifier verifier) {
        this.verifier = verifier;
    }

    public boolean verify(VerifiableJsonLd jsonLdWithProof) throws GeneralSecurityException {
        return verify(jsonLdWithProof.asJson());
    }

    public boolean verify(Map<String, Object> jsonLdObject) throws GeneralSecurityException {
        VerifiableJsonLd ldObject = new VerifiableJsonLd(jsonLdObject);
        LdProof proofs = ldObject.getProof();
        return this.verify(jsonLdObject, proofs);
    }

   public boolean verify(Map<String, Object> jsonLdObject, LdProof ldProof) throws GeneralSecurityException {

        LdProofBuilder builder = LdProofBuilder.fromLdProof(ldProof);
        String canonicalizedProof = builder.canonicalize();

        String canonicalizedDocument = VerifiableJsonLdBuilder.builder(jsonLdObject).buildCanonicalizedDocument();

        byte[] signingInput = new byte[64];
        System.arraycopy(SHAUtil.sha256(canonicalizedProof), 0, signingInput, 0, 32);
        System.arraycopy(SHAUtil.sha256(canonicalizedDocument), 0, signingInput, 32, 32);

        boolean verify = this.verify(signingInput, ldProof);

        // done

        return verify;
    }

    private boolean verify(byte[] signingInput, LdProof ldProof) throws GeneralSecurityException {
        try {
            String jws = ldProof.getJws();
            JWSObject detachedJwsObject = JWSObject.parse(jws);
            byte[] jwsSigningInput = JWSUtil.getJwsSigningInput(detachedJwsObject.getHeader(), signingInput);
            JWSVerifier jwsVerifier = new JoseJWSVerifier(verifier, JWSAlgorithm.RS256);
            return jwsVerifier.verify(detachedJwsObject.getHeader(), jwsSigningInput, detachedJwsObject.getSignature());

        } catch (JOSEException | ParseException ex) {
            throw new GeneralSecurityException("JOSE verification problem: " + ex.getMessage(), ex);
        }
    }

}
