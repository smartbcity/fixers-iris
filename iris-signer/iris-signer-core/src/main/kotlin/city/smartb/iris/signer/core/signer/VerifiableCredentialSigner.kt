package city.smartb.iris.signer.core.signer

import city.smartb.iris.crypto.dsl.signer.Signer
import city.smartb.iris.ldproof.LdProofBuilder
import city.smartb.iris.vc.VerifiableCredential
import city.smartb.iris.vc.VerifiableCredentialBuilder
import city.smartb.iris.vc.signer.VCSign
import java.time.LocalDateTime
import java.util.UUID

class VerifiableCredentialSigner {
    companion object {
        fun <T> sign(id: String, issuer: String, subject: T, signer: Signer): VerifiableCredential {
            val created = LocalDateTime.now()
            val nonce: String = UUID.randomUUID().toString()

            val vcBuild = VerifiableCredentialBuilder
                .create<T>()
                .withContextDefault()
                .withId(id)
                .withType("VerifiableCredential")
                .withIssuanceDate(created.toString())
                .withIssuer(issuer)
                .withCredentialSubject(subject)

            val proofBuilder = LdProofBuilder.builder()
                .withCreated(created)
                .withChallenge(nonce)
                .withProofPurpose("assertionMethod")
                .withVerificationMethod("$issuer")

            return VCSign().sign(vcBuild, proofBuilder, signer)
        }
    }
}
