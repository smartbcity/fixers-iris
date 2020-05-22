package city.smartb.iris.crypto.rsa;

import org.bouncycastle.util.io.pem.PemObject;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAKeyPairDecoderBase64 {

    public static KeyPair decode(String publicKey, String privateKey) throws InvalidKeyException {
        PublicKey pub = decodePublicKey(publicKey);
        PrivateKey priv = decodePrivateKey(privateKey);
        return new KeyPair(pub, priv);
    }

    public static RSAPrivateKey decodePrivateKey(String value) throws InvalidKeyException {
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(decode(value));
            return (RSAPrivateKey) kf.generatePrivate(keySpecPKCS8);
        } catch (Exception e) {
            throw new InvalidKeyException("Invalid private public key", e);
        }
    }

    public static RSAPublicKey decodePublicKey(String value) throws InvalidKeyException {
        try {
            byte[] bytes = decode(value);
            PemObject pem = new PemObject("PUBLIC KEY", bytes);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(pem.getContent());
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (Exception e) {
            throw new InvalidKeyException("Invalid jwt public key", e);
        }
    }

    private static byte[] decode(String value) {
        try {
            return Base64.getUrlDecoder().decode(value);
        } catch (Exception e) {
            return Base64.getDecoder().decode(value);
        }
    }


}
