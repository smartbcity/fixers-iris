package city.smartb.iris.crypto.rsa.verifier;

import com.nimbusds.jose.JWSAlgorithm;

import java.security.GeneralSecurityException;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;

public class RS256Verifier implements Verifier {

	private final RSAPublicKey rsaPublicKey;

	public RS256Verifier(RSAPublicKey rsaPublicKey) {
		this.rsaPublicKey = rsaPublicKey;
	}

	@Override
	public JWSAlgorithm getAlgorithm() {
		return  JWSAlgorithm.RS256;
	}

	@Override
	public boolean verify(byte[] content, byte[] signature) throws GeneralSecurityException {
		Signature jcaSignature = Signature.getInstance("SHA256withRSA");
		jcaSignature.initVerify(rsaPublicKey);
		jcaSignature.update(content);
		return jcaSignature.verify(signature);
	}
}
