package city.smartb.iris.ldproof.signer;

import city.smartb.iris.crypto.dsl.signer.Signer;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.impl.BaseJWSProvider;
import com.nimbusds.jose.util.Base64URL;

import java.security.GeneralSecurityException;
import java.util.Collections;

public class JoseJWSSigner extends BaseJWSProvider implements JWSSigner {

	private final Signer signer;

	public JoseJWSSigner(Signer signer, JWSAlgorithm algorithm) {
		super(Collections.singleton(algorithm));
		this.signer = signer;
	}

	@Override
	public Base64URL sign(final JWSHeader header, final byte[] signingInput) throws JOSEException {
		if (! this.supportedJWSAlgorithms().contains(header.getAlgorithm())) throw new JOSEException("Unexpected algorithm: " + header.getAlgorithm());
		try {
			return Base64URL.encode(this.signer.sign(signingInput));
		} catch (GeneralSecurityException ex) {
			throw new JOSEException(ex.getMessage(), ex);
		}
	}

}
