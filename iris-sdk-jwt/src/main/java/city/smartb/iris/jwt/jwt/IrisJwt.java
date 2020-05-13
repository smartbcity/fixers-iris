package city.smartb.iris.jwt.jwt;

import city.smartb.iris.jwt.exception.InvalidJwtException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class IrisJwt {

    private final SignedJWT jwt;
    private final JWTClaimsSet jwtClaimsSet;

    public IrisJwt(SignedJWT jwt, JWTClaimsSet jwtClaimsSet) {
        this.jwt = jwt;
        this.jwtClaimsSet = jwtClaimsSet;
    }

    public IrisPublicKey getPublicKey() throws InvalidJwtException {
        return IrisPublicKey.parse(jwtClaimsSet);
    }

    public String asBearer() {
        return "Bearer " + jwt.serialize();
    }

    public String asSerializeString() {
        return jwt.serialize();
    }


    public JWTClaimsSet getJwtClaimsSet() {
        return jwtClaimsSet;
    }

    public SignedJWT getSignedJWT() {
        return jwt;
    }
}
