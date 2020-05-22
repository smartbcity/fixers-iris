package city.smartb.iris.crypto.rsa;

import city.smartb.iris.crypto.rsa.exception.InvalidRsaKeyException;
import city.smartb.iris.crypto.rsa.utils.FileUtils;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.IOException;
import java.io.Reader;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAKeyPairReader {

    public static KeyPair loadKeyPair(String filename) throws InvalidRsaKeyException {
        PrivateKey priv = loadPrivateKey(filename);
        PublicKey pub = loadPublicKey(filename);
        return new KeyPair(pub, priv);
    }

    public static PrivateKey loadPrivateKey(String filename) throws InvalidRsaKeyException {
        try {
            PemObject pem = getPemObject(filename);
            RSAPrivateKey key = RSAPrivateKey.getInstance(pem.getContent());
            KeyFactory kf = KeyFactory.getInstance("RSA");
            RSAPrivateCrtKeySpec privSpec = new RSAPrivateCrtKeySpec(key.getModulus(), key.getPublicExponent(), key.getPrivateExponent(), key.getPrime1(), key.getPrime2(), key.getExponent1(), key.getExponent2(), key.getCoefficient());
            return kf.generatePrivate(privSpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            throw new InvalidRsaKeyException("Private key can't be loaded", e);
        }
    }

    public static PublicKey loadPublicKey(String filename) throws InvalidRsaKeyException {
        try {
            if (!filename.endsWith(".pub")) {
                filename = filename.concat(".pub");
            }
            PemObject pem = getPemObject(filename);
            byte[] pubKeyBytes = pem.getContent();

            X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubKeyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(pubSpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            throw new InvalidRsaKeyException("Public key can't be loaded", e);
        }
    }


    public static PublicKey fromByteArray(byte[] pub) throws InvalidRsaKeyException {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(pub));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new InvalidRsaKeyException("Public key can't be loaded", e);
        }
    }

    public static KeyPair generateRSAKey() throws InvalidRsaKeyException {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            return kpg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidRsaKeyException("RSA key can't be generated", e);
        }
    }

    private static PemObject getPemObject(String filename) throws IOException {
        Reader reader = FileUtils.getReader(filename);
        PemReader rpem = new PemReader(reader);
        return rpem.readPemObject();
    }

}

