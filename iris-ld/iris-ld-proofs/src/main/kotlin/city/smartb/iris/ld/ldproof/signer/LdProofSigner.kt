package city.smartb.iris.ld.ldproof.signer

import city.smartb.iris.crypto.dsl.signer.Signer
import city.smartb.iris.ld.ldproof.LdProof
import city.smartb.iris.ld.ldproof.LdProofBuilder
import city.smartb.iris.ld.ldproof.VerifiableJsonLdBuilder
import city.smartb.iris.ld.ldproof.util.JWSUtil
import city.smartb.iris.ld.ldproof.util.SHAUtil
import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSSigner
import java.security.GeneralSecurityException

abstract class LdProofSigner protected constructor(
    protected val signatureType: String,
    protected val signer: Signer,
    private val ldProofBuilder: LdProofBuilder
) {
    @Throws(GeneralSecurityException::class)
    fun sign(jsonLdObject: VerifiableJsonLdBuilder): LdProof {
        val canonicalizedDocument = jsonLdObject.buildCanonicalizedDocument()
        val canonicalizedProofOptions = ldProofBuilder.canonicalize(signer)
        val jws = sign(canonicalizedDocument, canonicalizedProofOptions)
        return ldProofBuilder.build(jws)
    }

    @Throws(GeneralSecurityException::class)
    private fun sign(canonicalizedDocument: String, canonicalizedProofOptions: String): String {
        val signingInput = ByteArray(64)
        System.arraycopy(SHAUtil.sha256(canonicalizedProofOptions), 0, signingInput, 0, 32)
        System.arraycopy(SHAUtil.sha256(canonicalizedDocument), 0, signingInput, 32, 32)
        return this.sign(signingInput)
    }

    @Throws(GeneralSecurityException::class)
    fun sign(signingInput: ByteArray): String {
        return try {
            val jwsHeader =
                JWSHeader.Builder(signer.algorithm).base64URLEncodePayload(false).criticalParams(setOf("b64")).build()
            val jwsSigningInput = JWSUtil.getJwsSigningInput(jwsHeader, signingInput)
            val jwsSigner: JWSSigner = JoseJWSSigner(signer, signer.algorithm)
            val signature = jwsSigner.sign(jwsHeader, jwsSigningInput)
            JWSUtil.serializeDetachedJws(jwsHeader, signature)
        } catch (ex: JOSEException) {
            throw GeneralSecurityException("Error signing ", ex)
        }
    }
}
