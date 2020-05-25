package city.smartb.iris.jwt;

import city.smartb.iris.crypto.rsa.RSAKeyPairGenerator;
import city.smartb.iris.crypto.rsa.exception.CryptoException;
import city.smartb.iris.jwt.base.DataTest;
import city.smartb.iris.jwt.generator.IrisJwtGenerator;
import city.smartb.iris.jwt.validator.IrisJwtError;
import city.smartb.iris.jwt.validator.IrisJwtValidator;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

class IrisJwtTest {

    private final String issuer = "iris.smartb.network";
    private final String audience = "https://iris.smartb.network";

    private final IrisJwtValidator validator = new IrisJwtValidator(issuer, audience);

    @Test
    void shouldBeValid() throws CryptoException, JOSEException, ParseException {
        DataTest dataTest = new DataTest();
        IrisJwt jwt = dataTest.generateSignedJWT();

        Assertions.assertThat(validator.validate(jwt)).isEmpty();
        Assertions.assertThat(validator.isValid(jwt)).isTrue();
    }

    @Test
    void shouldBeInvalid_WhenAudianceIsNotGood() throws JOSEException, ParseException, CryptoException {
        DataTest dataTest = new DataTest();
        KeyPair keyPair = RSAKeyPairGenerator.generate2048Pair();
        JWTClaimsSet claims = IrisJwtGenerator.generateClaimSet(keyPair.getPublic(), Optional.empty(), issuer, audience)
                .audience("NOT_VALID")
                .build();

        IrisJwt jwt = dataTest.signJWT(keyPair.getPrivate(), claims);

        Assertions.assertThat(validator.validate(jwt)).hasSize(1).contains(IrisJwtError.form("Audience[NOT_VALID] must contains "+ audience));
        Assertions.assertThat(validator.isValid(jwt)).isFalse();
    }

    @Test
    void shouldBeInvalid_whenExpirationDateIsBeforeNow() throws CryptoException, JOSEException, ParseException {
        DataTest dataTest = new DataTest();
        KeyPair keyPair = RSAKeyPairGenerator.generate2048Pair();
        JWTClaimsSet claims = IrisJwtGenerator.generateClaimSet(keyPair.getPublic(), Optional.empty(), issuer, audience)
                .expirationTime(getYesterday())
                .build();

        IrisJwt jwt = dataTest.signJWT(keyPair.getPrivate(), claims);

        Assertions.assertThat(validator.validate(jwt)).hasSize(1).contains(IrisJwtError.form("Expiration date must be in future"));
        Assertions.assertThat(validator.isValid(jwt)).isFalse();
    }

    @Test
    void shouldBeInvalid_whenIssuerIsNotValid() throws CryptoException, JOSEException, ParseException {
        DataTest dataTest = new DataTest();
        KeyPair keyPair = RSAKeyPairGenerator.generate2048Pair();
        JWTClaimsSet claims = IrisJwtGenerator.generateClaimSet(keyPair.getPublic(), Optional.empty(), issuer, audience)
                .issuer("NOT VALID")
                .build();

        IrisJwt jwt = dataTest.signJWT(keyPair.getPrivate(), claims);

        Assertions.assertThat(validator.validate(jwt)).hasSize(1).contains(IrisJwtError.form("Issuer[NOT VALID] must be equals to "+issuer));
        Assertions.assertThat(validator.isValid(jwt)).isFalse();
    }

    @Test
    void shouldBeInvalid_whenSubjectIsNotValid() throws CryptoException, JOSEException, ParseException {
        DataTest dataTest = new DataTest();
        KeyPair keyPair = RSAKeyPairGenerator.generate2048Pair();
        JWTClaimsSet claims = IrisJwtGenerator.generateClaimSet(keyPair.getPublic(), Optional.empty(), issuer, audience)
                .subject("NOT VALID")
                .build();

        IrisJwt jwt = dataTest.signJWT(keyPair.getPrivate(), claims);

        Assertions.assertThat(validator.validate(jwt)).hasSize(1).contains(IrisJwtError.form("Subject must be present: Unparsable jwt public key: NOT VALID"));
        Assertions.assertThat(validator.isValid(jwt)).isFalse();
    }

    private Date getYesterday() {
        LocalDateTime tomorrow = LocalDateTime.now().minusDays(1);
        return Date.from( tomorrow.atZone( ZoneId.systemDefault()).toInstant());
    }

}