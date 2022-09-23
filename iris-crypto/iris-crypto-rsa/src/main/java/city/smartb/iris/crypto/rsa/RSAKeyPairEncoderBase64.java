package city.smartb.iris.crypto.rsa;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class RSAKeyPairEncoderBase64 {

    public static String encodePrivateKey(RSAPrivateKey value) {
        return encode(value.getEncoded());
    }

    public static String encodePublicKey(RSAPublicKey value) {
        return encode(value.getEncoded());
    }

    private static String encode(byte[] value) {
        try {
            return Base64.getUrlEncoder().encodeToString(value);
        } catch (Exception e) {
            return Base64.getEncoder().encodeToString(value);
        }
    }


}
