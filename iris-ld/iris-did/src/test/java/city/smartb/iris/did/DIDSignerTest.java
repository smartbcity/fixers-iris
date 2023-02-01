//package city.smartb.iris.did;
//
//import city.smartb.iris.crypto.dsl.signer.Signer;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.security.GeneralSecurityException;
//import java.security.KeyPair;
//import java.security.interfaces.RSAPrivateKey;
//import java.time.LocalDateTime;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//class DIDSignerTest {
//
//    private DIDSigner vcSign = new DIDSigner();
//
//    @Test
//    void sign() throws GeneralSecurityException, InvalidRsaKeyException {
//        Map<String, Object> claims = new LinkedHashMap<>();
//        claims.put("name", "smartb");
//
//        DIDDocumentBuilder vcBuild = DIDDocumentBuilder
//                .create()
//                .withId("did:smartb:477599e2-eab4-4cd8-b4ab-75aad8a21f2e");
//        LdProofBuilder proofBuilder = LdProofBuilder.builder()
//                .withChallenge("Chalenges")
//                .withCreated(LocalDateTime.parse("2020-05-25T11:37:24.293"))
//                .withDomain("smartb.city")
//                .withProofPurpose("ProofPurpose")
//                .withVerificationMethod("VerificationMethod");
//        KeyPair pair = RSAKeyPairReader.INSTANCE.loadKeyPair("userAgentUnitTest");
//        Signer signer = new RS256Signer((RSAPrivateKey) pair.getPrivate());
//        DIDDocument cred = vcSign.sign(vcBuild, proofBuilder, signer);
//
//        Assertions.assertThat(cred.getProof()).isNotNull();
//        Assertions.assertThat(cred.getProof().getJws()).isEqualTo("eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJSUzI1NiJ9..QT2nS9mUkFIQnCHM5EJItJ5ZvlUZKoMQBeydJ9YtXypy228vrloDmAZr1fCeL1QEX8dnqfQrQFfHFeGdZIuD1pIsnMUdAbIBdlwCE_Dax8BzCRqXWaiCz6nsz5Bjbsbpc78DvUqF3ass28vkvZrhMYzoDtqjtQz4LMjRHBe0eAosp37pQHuo7v6hilPCWz82mzO2A7rI16AWDO0d9DUIkSWOShGQO22mA5UC9zXrTs3CvY0zNR4rJB_I7Akh75HjQpgOSALrkzy5yE8OCx09QGN69xT46qY7nsdG8-KWT6dThwjhslXIVXUvowZPld_oIOGUfpN8-bp_LxDnX7ZApA");
//        Assertions.assertThat(cred.getProof().getChallenge()).isEqualTo("Chalenges");
//        Assertions.assertThat(cred.getProof().getCreated()).isEqualTo("2020-05-25T11:37:24.293");
//        Assertions.assertThat(cred.getProof().getDomain()).isEqualTo("smartb.city");
//        Assertions.assertThat(cred.getProof().getProofPurpose()).isEqualTo("ProofPurpose");
//        Assertions.assertThat(cred.getProof().getType()).isEqualTo("RsaSignature2018");
//        Assertions.assertThat(cred.getProof().getVerificationMethod()).isEqualTo("VerificationMethod");
//
//    }
//}
