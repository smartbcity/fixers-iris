package city.smartb.iris.crypto.rsa.signer;

import com.nimbusds.jose.JWSAlgorithm;

import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPrivateKey;

public interface Signer {

    static RS256Signer rs256Signer(RSAPrivateKey rsaPrivateKey) {
        return new RS256Signer(rsaPrivateKey);
    }

    JWSAlgorithm getAlgorithm();
    String getTerm();

    byte[] sign(byte[] content) throws GeneralSecurityException;


}
