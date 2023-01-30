package city.smartb.iris.jwt.base;

import city.smartb.iris.crypto.rsa.RSAKeyPairGenerator;
import city.smartb.iris.crypto.rsa.exception.CryptoException;
import city.smartb.iris.jwt.IrisJwt;
import city.smartb.iris.jwt.generator.IrisJwtGenerator;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.text.ParseException;
import java.util.Optional;

public class DataTest {

    private final String issuer = "iris.smartb.network";
    private final String audience = "https://iris.smartb.network";

    public IrisJwt generateSignedJWT() throws JOSEException, ParseException, CryptoException {
        return generateSignedJWT(issuer, audience);
    }

    public IrisJwt generateSignedJWT(String issuer, String audience) throws JOSEException, ParseException, CryptoException {
        KeyPair keyPair = RSAKeyPairGenerator.INSTANCE.generate2048Pair();
        return generateSignedJWT(keyPair, issuer, audience);
    }

    public IrisJwt generateSignedJWT(KeyPair keyPair, String issuer, String audience) throws JOSEException, ParseException {
        JWTClaimsSet jwtClaims = IrisJwtGenerator.generateClaimSet(keyPair.getPublic(), Optional.empty(), issuer, audience).build();
        return signJWT(keyPair.getPrivate(), jwtClaims);
    }

    public IrisJwt signJWT(PrivateKey privKey, JWTClaimsSet jwtClaims) throws JOSEException, ParseException {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build();
        SignedJWT sjwt = new SignedJWT(header, jwtClaims);
        JWSSigner signer = new RSASSASigner(privKey);
        sjwt.sign(signer);
        return new IrisJwt(sjwt, sjwt.getJWTClaimsSet());
    }

}
