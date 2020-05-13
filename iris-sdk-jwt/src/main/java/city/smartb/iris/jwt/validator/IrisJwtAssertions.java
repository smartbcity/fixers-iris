package city.smartb.iris.jwt.validator;

import city.smartb.iris.jwt.IrisPublicKey;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class IrisJwtAssertions {

    public static void assertSignatureIsValid(SignedJWT jwt, IrisPublicKey publicKey, String expMsg, List<IrisJwtError> allErrors) {
        try {
            RSASSAVerifier verifier = publicKey.asRSASSAVerifier();
            boolean isValid = jwt.verify(verifier);
            if (!isValid) {
                allErrors.add(IrisJwtError.form(expMsg));
            }
        } catch (JOSEException e) {
            allErrors.add(IrisJwtError.form(expMsg, e));
        }
    }

    public static void assertNotExpired(Date expirationTime, String expMsg, List<IrisJwtError> allErrors) {
        if (new Date().toInstant().isAfter(expirationTime.toInstant())) {
            allErrors.add(IrisJwtError.form(expMsg));
        }
    }

    public static void assertContains(List<String> audience, String expectedValue, String expMsg, List<IrisJwtError> allErrors) {
        if (!audience.contains(expectedValue)) {
            allErrors.add(IrisJwtError.form(expMsg));
        }
    }

    public static void assertEquals(String issuer, String expectedValue, String expMsg, List<IrisJwtError> allErrors) {
        if (isBlank(issuer) || !issuer.equals(expectedValue)) {
            allErrors.add(IrisJwtError.form(expMsg));
        }
    }

    public static void assertStartWith(String issuer, String expectedValue, String expMsg, List<IrisJwtError> allErrors) {
        if (isBlank(issuer) || !issuer.startsWith(expectedValue)) {
            allErrors.add(IrisJwtError.form(expMsg));
        }
    }

    public static Optional<IrisPublicKey> assertExist(String subject, String expMsg, List<IrisJwtError> allErrors) {
        try {
            if (isBlank(subject)) {
                allErrors.add(IrisJwtError.form(expMsg));
            }
            return Optional.of(IrisPublicKey.parse(subject));
        } catch (Exception e) {
            allErrors.add(IrisJwtError.form(expMsg, e));
            return Optional.empty();
        }
    }

    public static boolean isBlank(String subject) {
        return subject == null || subject.trim().isEmpty();
    }
}
