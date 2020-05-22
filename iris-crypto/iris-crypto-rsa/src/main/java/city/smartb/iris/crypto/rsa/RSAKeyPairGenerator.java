package city.smartb.iris.crypto.rsa;

import city.smartb.iris.crypto.rsa.exception.CryptoException;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class RSAKeyPairGenerator {

    public static KeyPair generate2048Pair() throws CryptoException {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            return kpg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException("RSA key can't be generated", e);
        }
    }
}
