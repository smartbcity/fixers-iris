package city.smartb.iris.keypair.lib.service

import city.smartb.iris.crypto.dsl.signer.Signer
import city.smartb.iris.crypto.dsl.verifier.Verifier
import city.smartb.iris.crypto.hc.vault.transit.signer.VaultTransitSigner
import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64
import city.smartb.iris.crypto.rsa.signer.RS256Signer
import city.smartb.iris.crypto.rsa.verifier.RS256Verifier
import city.smartb.iris.keypair.domain.PublicKeyGetQuery
import city.smartb.iris.keypair.domain.PublicKeyGetResult
import city.smartb.iris.keypair.domain.SignQuery
import city.smartb.iris.keypair.domain.SignResult
import city.smartb.iris.keypair.domain.VerifyQuery
import city.smartb.iris.keypair.domain.VerifyResult
import city.smartb.iris.ld.ldproof.LdProofBuilder
import city.smartb.iris.ld.ldproof.VerifiableJsonLdBuilder
import city.smartb.iris.ld.ldproof.crypto.RsaSignature2018LdProofSigner
import city.smartb.iris.ld.ldproof.crypto.RsaSignature2018LdProofVerifier
import city.smartb.iris.vault.client.VaultClient
import city.smartb.iris.vault.domain.queries.TransitPublicKeyGetQuery
import java.security.interfaces.RSAPrivateKey
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class KeypairFinderService(
    private val vaultClient: VaultClient
) {
    suspend fun publicKeyGet(query: PublicKeyGetQuery): PublicKeyGetResult {
        val publicKey = vaultClient.transitPublicKeyGet(TransitPublicKeyGetQuery(
            keyName = query.keyName
        )).publicKey

        return PublicKeyGetResult(publicKey)
    }

    fun verify(query: VerifyQuery): VerifyResult {
        val proofType = query.jsonLd.proof.type

        val verifier = getVerifier(proofType, query.publicKey)
        val ldSigner = RsaSignature2018LdProofVerifier(verifier)

        return VerifyResult(ldSigner.verify(query.jsonLd))
    }

    suspend fun sign(query: SignQuery): SignResult {
        val signer = getSigner(query.method, query.privateKey)

        val created = LocalDateTime.now()
        val nonce: String = UUID.randomUUID().toString()
        val proofBuilder = LdProofBuilder.builder()
            .withCreated(created)
            .withChallenge(nonce)
            .withProofPurpose("assertionMethod")
            .withVerificationMethod(query.pathToVerificationKey)

        val ldSigner = RsaSignature2018LdProofSigner(signer, proofBuilder)
        val builder = VerifiableJsonLdBuilder.builder(query.jsonLd.asJson())
        val proof = ldSigner.sign(builder)
        val verifiableJsonLd = builder.build(proof)

        return SignResult(verifiableJsonLd)
    }

    private fun getSigner(method: String, privateKey: Any): Signer {
        return when (method) {
            "rsa" -> RS256Signer(privateKey as RSAPrivateKey)
            "transit" -> VaultTransitSigner(privateKey as String, vaultClient)
            else -> {
                throw Exception("Signer type not found")
            }
        }
    }

    private fun getVerifier(type: String, publicKey: String): Verifier {
        return when(type) {
            "RsaSignature2018" -> RS256Verifier(RSAKeyPairDecoderBase64.decodePublicKey(publicKey))
            else -> throw Exception("Verifier type not found")
        }
    }

    private fun parseRSAPrivateKey(privKey: String): RSAPrivateKey {
        return try {
            RSAKeyPairDecoderBase64.decodePrivateKey(privKey)
        } catch (e: Exception) {
            RSAKeyPairDecoderBase64.decodePrivateKey(pemPrivateKeyToString(privKey))
        }
    }

    private fun pemPrivateKeyToString(pemString: String): String {
        return pemString
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace(System.lineSeparator(), "")
            .replace("-----END PRIVATE KEY-----", "")
    }
}
