package city.smartb.iris.vault.lib

import city.smartb.iris.crypto.rsa.RSAKeyPairReader
import city.smartb.iris.crypto.rsa.signer.RS256Signer
import city.smartb.iris.crypto.rsa.verifier.RS256Verifier
import city.smartb.iris.ld.jsonld.JsonLdConsts
import city.smartb.iris.ld.jsonld.JsonLdObject
import city.smartb.iris.ld.ldproof.LdProof
import city.smartb.iris.ld.ldproof.LdProofBuilder
import city.smartb.iris.ld.ldproof.VerifiableJsonLdBuilder
import city.smartb.iris.ld.ldproof.crypto.RsaSignature2018LdProofSigner
import city.smartb.iris.ld.ldproof.crypto.RsaSignature2018LdProofVerifier
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class IrisVaultFeaturesImplTest {

    @Test
    fun sign(): Unit = runBlocking {
        val pair = RSAKeyPairReader.loadKeyPair("userAgentUnitTest")
        val signer = RS256Signer(pair.private as RSAPrivateKey)

        val mapAttributes = mapOf(
            JsonLdConsts.CONTEXT to arrayOf("https://schema.org"),
            JsonLdConsts.TYPE to "Person",
            "givenName" to "Teddy",
            "familyName" to "Le"
        )

        val jsonLd = JsonLdObject(mapAttributes)

        val proofBuilder = LdProofBuilder.builder()
            .withCreated("creationDate")
            .withChallenge("challenge")
            .withProofPurpose("assertionMethod")
            .withVerificationMethod("pathtoverificationkey")

        val ldSigner = RsaSignature2018LdProofSigner(signer, proofBuilder)
        val builder = VerifiableJsonLdBuilder.builder(jsonLd.asJson())

        val proof = ldSigner.sign(builder)
        val verifiableJsonLd = builder.build(proof)

        Assertions.assertThat(verifiableJsonLd.proof.jws).isEqualTo("eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJSUzI1NiJ9..jsmrkKq924veBMjEzX38cc5vbrXCGRUJ500VjaMAUAbEyYVHSGL44AY_4phKDtL8xBHU4vzy_-6Tdcs2PHqc4wWqJmvlkI_MnFGH44uikc2UeyzLfL-W53668nrp5F2nOsY2Nqraz50xcF_vJew16uiX3m8VMjG8ULGSk9GcpBWiJnYAYyfpNnNpleG1Ty_C3IXG7kvNjptE1qy26xj1iNliXHUs3eoMHqOicRWHPObVCA477bYF-5nl6qCfhSNT5WduhNHJLAEZQ9YwEGhhilRV0lyhZE43HexXMiAiW6dsMntUKWk-xEmhzvKVGk11TqSYI8tWb0iHKpVUvHyD8g")
    }

    @Test
    fun verifyIsTrue(): Unit {
        // TODO
        val proofAttributes = mapOf(
            LdProof.JSON_LD_CREATED to "creationDate",
            LdProof.JSON_LD_CHALLENGE to "challenge",
            LdProof.JSON_LD_PURPOSE to "assertionMethod",
            LdProof.JSON_LD_VERIFICATION_METHOD to "pathtoverificationkey",
            LdProof.JSON_LD_JWS to "eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJSUzI1NiJ9..jsmrkKq924veBMjEzX38cc5vbrXCGRUJ500VjaMAUAbEyYVHSGL44AY_4phKDtL8xBHU4vzy_-6Tdcs2PHqc4wWqJmvlkI_MnFGH44uikc2UeyzLfL-W53668nrp5F2nOsY2Nqraz50xcF_vJew16uiX3m8VMjG8ULGSk9GcpBWiJnYAYyfpNnNpleG1Ty_C3IXG7kvNjptE1qy26xj1iNliXHUs3eoMHqOicRWHPObVCA477bYF-5nl6qCfhSNT5WduhNHJLAEZQ9YwEGhhilRV0lyhZE43HexXMiAiW6dsMntUKWk-xEmhzvKVGk11TqSYI8tWb0iHKpVUvHyD8g"
        )
        val mapAttributes = mapOf(
            JsonLdConsts.CONTEXT to arrayOf("https://schema.org"),
            JsonLdConsts.TYPE to "Person",
            "givenName" to "Teddy",
            "familyName" to "Le"
        )

        val verifiableJsonLd = VerifiableJsonLdBuilder.builder(mapAttributes).build(LdProof(proofAttributes))

        val pair = RSAKeyPairReader.loadKeyPair("userAgentUnitTest")
        val verifier = RS256Verifier(pair.public as RSAPublicKey)

        val ldSigner = RsaSignature2018LdProofVerifier(verifier)

        val isValid = ldSigner.verify(verifiableJsonLd)

        Assertions.assertThat(isValid).isEqualTo(true)
    }

    @Test
    fun verifyIsFalse(): Unit {
        // TODO
        val proofAttributes = mapOf(
            LdProof.JSON_LD_CREATED to "creationDate",
            LdProof.JSON_LD_CHALLENGE to "challenge",
            LdProof.JSON_LD_PURPOSE to "assertionMethod",
            LdProof.JSON_LD_VERIFICATION_METHOD to "pathtoverificationkey",
            LdProof.JSON_LD_JWS to "eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJSUzI1NiJ9..jsmrkKq924veBMjEzX38cc5vbrXCGRUJ500VjaMAUAbEyYVHSGL44AY_4phKDtL8xBHU4vzy_-6Tdcs2PHqc4wWqJmvlkI_MnFGH44uikc2UeyzLfL-W53668nrp5F2nOsY2Nqraz50xcF_vJew16uiX3m8VMjG8ULGSk9GcpBWiJnYAYyfpNnNpleG1Ty_C3IXG7kvNjptE1qy26xj1iNliXHUs3eoMHqOicRWHPObVCA477bYF-5nl6qCfhSNT5WduhNHJLAEZQ9YwEGhhilRV0lyhZE43HexXMiAiW6dsMntUKWk-xEmhzvKVGk11TqSYI8tWb0iHKpVUvHyD8g"
        )
        val mapAttributes = mapOf(
            JsonLdConsts.CONTEXT to arrayOf("https://schema.org"),
            JsonLdConsts.TYPE to "Person",
            "givenName" to "Teddy",
            "familyName" to "Lee"
        )

        val verifiableJsonLd = VerifiableJsonLdBuilder.builder(mapAttributes).build(LdProof(proofAttributes))

        val pair = RSAKeyPairReader.loadKeyPair("userAgentUnitTest")
        val verifier = RS256Verifier(pair.public as RSAPublicKey)

        val ldSigner = RsaSignature2018LdProofVerifier(verifier)

        val isValid = ldSigner.verify(verifiableJsonLd)

        Assertions.assertThat(isValid).isEqualTo(false)
    }
}
