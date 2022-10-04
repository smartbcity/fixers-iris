package city.smartb.iris.jwt;

import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64;
import city.smartb.iris.jwt.exception.InvalidJwtException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;

import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class IrisPublicKey {

    private final RSAPublicKey publicKey;

    public static IrisPublicKey parse(JWTClaimsSet jwtClaimsSet) throws InvalidJwtException {
        String sub = jwtClaimsSet.getSubject();
        return parse(sub);
    }

    public static IrisPublicKey formPublicKey(RSAPublicKey publicKey) {
        return new IrisPublicKey(publicKey);
    }

    public static IrisPublicKey parse(String value) throws InvalidJwtException {
        try {
            RSAPublicKey pubKey  = RSAKeyPairDecoderBase64.INSTANCE.decodePublicKey(value);
            return new IrisPublicKey(pubKey);
        } catch (Exception e) {
            throw new InvalidJwtException("Unparsable jwt public key: "+value, e);
        }
    }


    public IrisPublicKey(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }


    public RSASSAVerifier asRSASSAVerifier() {
        return new RSASSAVerifier(publicKey);
    }

    public String asString() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
}
