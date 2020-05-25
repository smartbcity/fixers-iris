package city.smartb.iris.crypto.rsa.signer;

import com.nimbusds.jose.JWSAlgorithm;

import java.security.GeneralSecurityException;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;

public class RS256Signer implements Signer {

	private final RSAPrivateKey rsaPrivateKey;

	public RS256Signer(RSAPrivateKey rsaPrivateKey) {
		this.rsaPrivateKey = rsaPrivateKey;
	}

	@Override
	public JWSAlgorithm getAlgorithm() {
		return  JWSAlgorithm.RS256;
	}

	@Override
	public String getTerm() {
		return "RsaSignature2018";
	}

	@Override
	public byte[] sign(byte[] content) throws GeneralSecurityException {
		Signature jcaSignature = Signature.getInstance("SHA256withRSA");
		jcaSignature.initSign(rsaPrivateKey);
		jcaSignature.update(content);
		return jcaSignature.sign();
	}
}
