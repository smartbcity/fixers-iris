package city.smartb.iris.jwt.parser;

import city.smartb.iris.jwt.exception.InvalidJwtException;
import city.smartb.iris.jwt.jwt.IrisJwt;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public class IrisJwtParser {

    public static IrisJwt parse(String value) throws InvalidJwtException {
        try {
            SignedJWT signedJWT = SignedJWT.parse(value);
            return new IrisJwt(signedJWT, signedJWT.getJWTClaimsSet());
        } catch (ParseException e) {
            throw new InvalidJwtException("Invalid jwt", e);
        }
    }
}
