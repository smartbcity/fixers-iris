package city.smartb.iris.ldproof.signer;

import java.security.GeneralSecurityException;
import java.util.Collections;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.util.Base64URL;

import city.smartb.iris.crypto.rsa.signer.Signer;
import city.smartb.iris.ldproof.LdJsonObjectBuilder;
import city.smartb.iris.ldproof.LdProof;
import city.smartb.iris.ldproof.LdProofBuilder;
import city.smartb.iris.ldproof.util.JWSUtil;
import city.smartb.iris.ldproof.util.SHAUtil;

public abstract class LdProofSigner {

	private final LdProofBuilder ldProofBuilder;

	protected final String signatureType;
	protected final Signer signer;

	protected LdProofSigner(String signatureType, Signer signer, LdProofBuilder ldProofBuilder) {
		this.signatureType = signatureType;
		this.signer = signer;
		this.ldProofBuilder = ldProofBuilder;
	}

	public LdProof sign(LdJsonObjectBuilder jsonLdObject) throws GeneralSecurityException {
		String canonicalizedDocument = jsonLdObject.buildCanonicalizedDocument();
		String canonicalizedProofOptions = ldProofBuilder.canonicalize(signer);
		String jws = sign(canonicalizedDocument, canonicalizedProofOptions);
		return ldProofBuilder.build(jws);
	}

	private String sign(String canonicalizedDocument, String canonicalizedProofOptions) throws GeneralSecurityException {
		byte[] signingInput = new byte[64];
		System.arraycopy(SHAUtil.sha256(canonicalizedProofOptions), 0, signingInput, 0, 32);
		System.arraycopy(SHAUtil.sha256(canonicalizedDocument), 0, signingInput, 32, 32);
		return this.sign(signingInput);
	}

	public String sign(byte[] signingInput) throws GeneralSecurityException {
		try {

			JWSHeader jwsHeader = new JWSHeader.Builder(signer.getAlgorithm()).base64URLEncodePayload(false).criticalParams(Collections.singleton("b64")).build();
			byte[] jwsSigningInput = JWSUtil.getJwsSigningInput(jwsHeader, signingInput);

			JWSSigner jwsSigner = new JoseJWSSigner(signer, signer.getAlgorithm());
			Base64URL signature = jwsSigner.sign(jwsHeader, jwsSigningInput);
			return JWSUtil.serializeDetachedJws(jwsHeader, signature);

		} catch (JOSEException ex) {
			throw new GeneralSecurityException("Error signing ", ex);
		}
	}

}
