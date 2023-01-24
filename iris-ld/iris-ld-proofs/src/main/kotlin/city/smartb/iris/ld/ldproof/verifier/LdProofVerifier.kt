package city.smartb.iris.ld.ldproof.verifier

import city.smartb.iris.crypto.dsl.verifier.Verifier
import city.smartb.iris.ld.ldproof.LdProof
import city.smartb.iris.ld.ldproof.LdProofBuilder
import city.smartb.iris.ld.ldproof.VerifiableJsonLd
import city.smartb.iris.ld.ldproof.VerifiableJsonLdBuilder
import city.smartb.iris.ld.ldproof.util.JWSUtil
import city.smartb.iris.ld.ldproof.util.SHAUtil
import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSObject
import com.nimbusds.jose.JWSVerifier
import java.security.GeneralSecurityException
import java.text.ParseException

abstract class LdProofVerifier protected constructor(private val verifier: Verifier) {
    @Throws(GeneralSecurityException::class)
    fun verify(jsonLdWithProof: VerifiableJsonLd): Boolean {
        return verify(jsonLdWithProof.asJson())
    }

    @Throws(GeneralSecurityException::class)
    fun verify(jsonLdObject: Map<String, Any>): Boolean {
        val ldObject = VerifiableJsonLd(jsonLdObject)
        val proofs = ldObject.proof
        return this.verify(jsonLdObject, proofs)
    }

    @Throws(GeneralSecurityException::class)
    fun verify(
        jsonLdObject: Map<String, Any>,
        ldProof: LdProof
    ): Boolean {
        val jws = ldProof.jws!!
        val builder: LdProofBuilder = LdProofBuilder.Companion.fromLdProof(ldProof)
        val canonicalizedProof = builder.canonicalize()
        val canonicalizedDocument: String =
            VerifiableJsonLdBuilder.Companion.builder(jsonLdObject).buildCanonicalizedDocument()
        val signingInput = ByteArray(64)
        System.arraycopy(SHAUtil.sha256(canonicalizedProof), 0, signingInput, 0, 32)
        System.arraycopy(SHAUtil.sha256(canonicalizedDocument), 0, signingInput, 32, 32)

        // done
        return verify(signingInput, jws)
    }

    @Throws(GeneralSecurityException::class)
    private fun verify(signingInput: ByteArray, jws: String): Boolean {
        return try {
            val detachedJwsObject = JWSObject.parse(jws)
            val jwsSigningInput = JWSUtil.getJwsSigningInput(detachedJwsObject.header, signingInput)
            val jwsVerifier: JWSVerifier = JoseJWSVerifier(verifier, JWSAlgorithm.RS256)
            jwsVerifier.verify(detachedJwsObject.header, jwsSigningInput, detachedJwsObject.signature)
        } catch (ex: JOSEException) {
            throw GeneralSecurityException("JOSE verification problem: " + ex.message, ex)
        } catch (ex: ParseException) {
            throw GeneralSecurityException("JOSE verification problem: " + ex.message, ex)
        }
    }
}
