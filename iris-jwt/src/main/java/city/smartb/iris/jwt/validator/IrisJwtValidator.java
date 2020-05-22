package city.smartb.iris.jwt.validator;

import city.smartb.iris.jwt.IrisJwt;
import city.smartb.iris.jwt.IrisPublicKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static city.smartb.iris.jwt.validator.IrisJwtAssertions.*;

public class IrisJwtValidator {

    private final String issuer;
    private final String audience;

    public IrisJwtValidator(String issuer, String audience) {
        this.issuer = issuer;
        this.audience = audience;
    }

    public Boolean isValid(IrisJwt jwt) {
        List<IrisJwtError> errors = validate(jwt);
        return errors.size() == 0;
    }

    public List<IrisJwtError> validate(IrisJwt jwt) {
        List<IrisJwtError> allErrors = new ArrayList<>();
        assertStartWith(jwt.getJwtClaimsSet().getIssuer(), issuer, "Issuer["+jwt.getJwtClaimsSet().getIssuer()+"] must be equals to "+issuer, allErrors);
        assertContains(jwt.getJwtClaimsSet().getAudience(), audience, "Audience"+jwt.getJwtClaimsSet().getAudience()+" must contains "+audience, allErrors);
        assertNotExpired(jwt.getJwtClaimsSet().getExpirationTime(), "Expiration date must be in future", allErrors);

        Optional<IrisPublicKey> publicKey = assertExist(jwt.getJwtClaimsSet().getSubject(), "Subject must be present", allErrors);
        publicKey.ifPresent(irisPublicKey ->
                assertSignatureIsValid(jwt.getSignedJWT(), irisPublicKey, "Signature has not been issued by issuer publickey", allErrors)
        );
        return allErrors;

    }
}
