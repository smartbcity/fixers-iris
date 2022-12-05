//package city.smartb.iris.vc.signer;
//
//import java.security.GeneralSecurityException;
//import java.security.KeyPair;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import city.smartb.iris.crypto.dsl.signer.Signer;
//import city.smartb.iris.crypto.dsl.verifier.Verifier;
//import city.smartb.iris.crypto.rsa.signer.RS256Signer;
//import city.smartb.iris.crypto.rsa.verifier.RS256Verifier;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import com.google.common.collect.ImmutableList;
//
//import city.smartb.iris.crypto.rsa.RSAKeyPairReader;
//import city.smartb.iris.crypto.rsa.exception.InvalidRsaKeyException;
//import city.smartb.iris.ldproof.LdProofBuilder;
//import city.smartb.iris.vc.VerifiableCredential;
//import city.smartb.iris.vc.VerifiableCredentialBuilder;
//
//class VCSignTest {
//
//    private VCVerifier vcVerifier = new VCVerifier();
//    private VCSign vcSign = new VCSign();
//
//    @Test
//    void sign() throws GeneralSecurityException, InvalidRsaKeyException {
//        Map<String, Object> claims = new LinkedHashMap<>();
//        claims.put("name", "smartb");
//        claims.put("amount", "666");
//        claims.put("azerty", "ok");
//
//        List<String> context = new ArrayList<>();
//        context.add(VerifiableCredentialBuilder.VC_DEFAULT_CONTEXT);
//        context.add(getClass().getResource("/context.json").toString());
//
//        VerifiableCredentialBuilder vcBuild = VerifiableCredentialBuilder
//                .create()
//                .withContext(context)
//                .withType("VerifiableCredential")
//                .withId("did:477599e2-eab4-4cd8-b4ab-75aad8a21f2e")
//                .withIssuanceDate("2020-05-25T11:37:24.293")
//                .withIssuer("did:UnitTest")
//                .withCredentialSubject(claims);
//
//        LdProofBuilder proofBuilder = LdProofBuilder.builder()
//                .withChallenge("Chalenges")
//                .withCreated(LocalDateTime.parse("2020-05-25T11:37:24.293"))
//                .withDomain("smartb.city")
//                .withProofPurpose("ProofPurpose")
//                .withVerificationMethod("VerificationMethod");
//
//        KeyPair pair = RSAKeyPairReader.INSTANCE.loadKeyPair("userAgentUnitTest");
//        Signer signer = new RS256Signer((RSAPrivateKey) pair.getPrivate());
//
//        VerifiableCredential cred = vcSign.sign(vcBuild, proofBuilder, signer);
//
//        Assertions.assertThat(cred.getProof()).isNotNull();
//        Assertions.assertThat(cred.getProof().getJws()).isEqualTo("eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJSUzI1NiJ9..SPmzzdcRYtaoX2H6CqNNXHcTWRx8rJrkZNShxWv7kYov2suNawKR-_jtjff2tpoGhy8pRhljWeDojOm5beW3olY8I66fgMulADF3AIwZDD_KVPpRRCVJ0a2kf7OHX7RHtj9y3QKLcAb_hl3QK-ssnktzLF59CuvlMc1lnvmXTFE0i_BQhqeY_UTqi1vTYLCawOwTjUoC0EsvEfeLMCjqcLS73GoI_vK_IAQk4YZ4IEiqyrfxDhF715t7kMn6YpKmzlNTR9QLEq4qbY3lNRfLnpxw-lysjfu0oMPUtr5gf576u4-EivETE7IR_RoA2cBOhMWEd6Kp-YNFPeLddAQiYw");
//        Assertions.assertThat(cred.getProof().getChallenge()).isEqualTo("Chalenges");
//        Assertions.assertThat(cred.getProof().getCreated()).isEqualTo("2020-05-25T11:37:24.293");
//        Assertions.assertThat(cred.getProof().getDomain()).isEqualTo("smartb.city");
//        Assertions.assertThat(cred.getProof().getProofPurpose()).isEqualTo("ProofPurpose");
//        Assertions.assertThat(cred.getProof().getType()).isEqualTo("RsaSignature2018");
//        Assertions.assertThat(cred.getProof().getVerificationMethod()).isEqualTo("VerificationMethod");
//    }
//
//    @Test
//    void verify() throws GeneralSecurityException, InvalidRsaKeyException {
//        Map<String, Object> claims = new LinkedHashMap<>();
//        claims.put("name", "smartb");
//
//        VerifiableCredentialBuilder vcBuild = VerifiableCredentialBuilder
//                .create()
//                .withId("477599e2-eab4-4cd8-b4ab-75aad8a21f2e")
//                .withIssuanceDate("2020-05-25T11:37:24.293")
//                .withIssuer("UnitTest")
//                .withCredentialSubject(claims);
//
//        LdProofBuilder proofBuilder = LdProofBuilder.builder()
//                .withChallenge("Chalenges")
//                .withCreated(LocalDateTime.parse("2020-05-25T11:37:24.293"))
//                .withDomain("smartb.city")
//                .withProofPurpose("ProofPurpose")
//                .withVerificationMethod("VerificationMethod");
//
//        KeyPair pair = RSAKeyPairReader.INSTANCE.loadKeyPair("userAgentUnitTest");
//        Signer signer = new RS256Signer((RSAPrivateKey) pair.getPrivate());
//
//        VerifiableCredential cred = vcSign.sign(vcBuild, proofBuilder, signer);
//
//        Verifier verifier = new RS256Verifier((RSAPublicKey) pair.getPublic());
//        Boolean isValid = vcVerifier.verify(cred, verifier);
//
//        Assertions.assertThat(isValid).isTrue();
//    }
//
//
//    @Test
//    void verifyVCContainsCustomValue() throws GeneralSecurityException, InvalidRsaKeyException {
//        Map<String, Object> claims = new LinkedHashMap<>();
//        claims.put("name", "smartb");
//
//        VerifiableCredentialBuilder vcBuild = VerifiableCredentialBuilder
//                .create()
//                .withId("477599e2-eab4-4cd8-b4ab-75aad8a21f2e")
//                .withIssuanceDate("2020-05-25T11:37:24.293")
//                .withIssuer("UnitTest")
//                .withCredentialSubject(claims)
//                .with("custom", VCRef.list());
//
//        LdProofBuilder proofBuilder = LdProofBuilder.builder()
//                .withChallenge("Chalenges")
//                .withCreated(LocalDateTime.parse("2020-05-25T11:37:24.293"))
//                .withDomain("smartb.city")
//                .withProofPurpose("ProofPurpose")
//                .withVerificationMethod("VerificationMethod");
//
//        KeyPair pair = RSAKeyPairReader.INSTANCE.loadKeyPair("userAgentUnitTest");
//        Signer signer = new RS256Signer((RSAPrivateKey) pair.getPrivate());
//
//        VerifiableCredential cred = vcSign.sign(vcBuild, proofBuilder, signer);
//
//        Verifier verifier = new RS256Verifier((RSAPublicKey) pair.getPublic());
//        Boolean isValid = vcVerifier.verify(cred, verifier);
//
//        Assertions.assertThat(isValid).isTrue();
//        Assertions.assertThat(cred.get("custom").asListObjects(VCRef.class)).hasSize(2);
//    }
//
//    public static class VCRef {
//        public static List<VCRef> list() {
//            return ImmutableList.of(
//                    new VCRef().setId("1").setJws("jws1"),
//                    new VCRef().setId("2").setJws("jws2")
//            );
//        }
//
//        private String jws;
//        private String id;
//
//        public String getJws() {
//            return jws;
//        }
//
//        public VCRef setJws(String jws) {
//            this.jws = jws;
//            return this;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public VCRef setId(String id) {
//            this.id = id;
//            return this;
//        }
//    }
//}
