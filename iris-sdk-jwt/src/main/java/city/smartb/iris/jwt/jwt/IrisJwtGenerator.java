package city.smartb.iris.jwt.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class IrisJwtGenerator {

    public static IrisJwt generate(KeyPair keyPair, Optional<String> email) throws ParseException, JOSEException {
        return  generate(keyPair, email, "iris.smartb.network", "https://iris.smartb.network");
    }

    public static IrisJwt generate(KeyPair keyPair, Optional<String> email, String issuer, String audience) throws ParseException, JOSEException {
        JWTClaimsSet jwtClaims = generateClaimSet(keyPair.getPublic(), email, issuer, audience).build();
        return  signJWT(keyPair.getPrivate(), jwtClaims);
    }

    public static JWTClaimsSet.Builder generateClaimSet(PublicKey publicKey, Optional<String> email, String issuer, String audience) {
        return new JWTClaimsSet.Builder()
                .issuer(issuer)
                .subject(Base64.getEncoder().encodeToString(publicKey.getEncoded()))
                .claim("email", email.orElse(null))
                .audience(Collections.singletonList(audience))
                .expirationTime(getInOneMonth())
                .issueTime(Calendar.getInstance().getTime())
                .jwtID(UUID.randomUUID().toString());
    }


    private static Date getInOneMonth() {
        LocalDateTime tomorrow = LocalDateTime.now().plusMonths(1);
        return Date.from(tomorrow.atZone(ZoneId.systemDefault()).toInstant());
    }

    static IrisJwt signJWT(PrivateKey privKey, JWTClaimsSet jwtClaims) throws JOSEException, ParseException {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build();
        SignedJWT sjwt = new SignedJWT(header, jwtClaims);
        JWSSigner signer = new RSASSASigner(privKey);
        sjwt.sign(signer);
        return new IrisJwt(sjwt, sjwt.getJWTClaimsSet());
    }

}
