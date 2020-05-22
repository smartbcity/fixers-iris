package city.smartb.iris.vc.signer;

import city.smartb.iris.crypto.rsa.RSAKeyPairGenerator;
import city.smartb.iris.crypto.rsa.exception.CryptoException;
import city.smartb.iris.crypto.rsa.signer.Signer;
import city.smartb.iris.ldproof.LdProofBuilder;
import city.smartb.iris.vc.VerifiableCredential;
import city.smartb.iris.vc.VerifiableCredentialBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

class VCSignTest {

    private VCSign vcSign = new VCSign();

    @Test
    void sign() throws GeneralSecurityException, CryptoException {
        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put("name", "smartb");

        VerifiableCredentialBuilder vcBuild = VerifiableCredentialBuilder
                .create()
                .withId(UUID.randomUUID().toString())
                .withIssuanceDate(LocalDate.now().toString())
                .withIssuer("UnitTest")
                .withCredentialSubject(claims);
        LdProofBuilder proofBuilder = LdProofBuilder.builder()
                .withChallenge("Chalenges")
                .withCreated(new Date())
                .withDomain("smartb.city")
                .withProofPurpose("ProofPurpose")
                .withVerificationMethod("VerificationMethod");
        KeyPair pair = RSAKeyPairGenerator.generate2048Pair();
        Signer signer = Signer.rs256Signer((RSAPrivateKey) pair.getPrivate());
        VerifiableCredential cred = vcSign.sign(vcBuild, proofBuilder, signer);
        Assertions.assertThat(cred.getProof()).isNotEmpty();
    }
}