package city.smartb.iris.signer.core.signer

import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64
import city.smartb.iris.crypto.rsa.signer.Signer
import city.smartb.iris.ldproof.LdProofBuilder
import city.smartb.iris.vc.VerifiableCredential
import city.smartb.iris.vc.VerifiableCredentialBuilder
import city.smartb.iris.vc.signer.VCSign
import java.security.interfaces.RSAPrivateKey
import java.time.LocalDateTime
import java.util.UUID

class VerifiableCredentialSigner {
    companion object {
        fun <T> sign(id: String, issuer: String, subject: T, privateKey: String, keyName: String): VerifiableCredential {
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
                .withVerificationMethod("$issuer#$keyName")

            val priv = privateKey.toRSAPrivateKey()


            return VCSign().sign(vcBuild, proofBuilder, Signer.rs256Signer(priv))
        }

        private fun String.toRSAPrivateKey(): RSAPrivateKey {
            return RSAKeyPairDecoderBase64.decodePrivateKey(this)
        }
    }
}
