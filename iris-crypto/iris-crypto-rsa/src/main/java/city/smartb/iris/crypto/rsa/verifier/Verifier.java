package city.smartb.iris.crypto.rsa.verifier;

import com.nimbusds.jose.JWSAlgorithm;

import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPublicKey;

public interface Verifier {

    static RS256Verifier rs256Verifier(RSAPublicKey rsaPublicKey) {
        return new RS256Verifier(rsaPublicKey);
    }

    JWSAlgorithm getAlgorithm();

    boolean verify(byte[] content, byte[] signature) throws GeneralSecurityException;

}
