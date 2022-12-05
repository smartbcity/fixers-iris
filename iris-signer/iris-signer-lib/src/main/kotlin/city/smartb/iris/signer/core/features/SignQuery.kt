package city.smartb.iris.signer.core.features

import city.smartb.iris.crypto.dsl.signer.Signer
import city.smartb.iris.crypto.hc.vault.transit.signer.VaultTransitSigner
import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64
import city.smartb.iris.crypto.rsa.signer.RS256Signer
import city.smartb.iris.jsonld.JsonLdObject
import city.smartb.iris.ldproof.LdProofBuilder
import city.smartb.iris.ldproof.VerifiableJsonLd
import city.smartb.iris.ldproof.VerifiableJsonLdBuilder
import city.smartb.iris.ldproof.crypto.RsaSignature2018LdProofSigner
import city.smartb.iris.signer.domain.features.SignQueryFunction
import city.smartb.iris.signer.domain.features.SignQueryResult
import city.smartb.iris.vault.client.VaultClient
import f2.dsl.fnc.f2Function
import java.security.interfaces.RSAPrivateKey
import java.time.LocalDateTime
import java.util.UUID
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SignQueryFunctionImpl(
    private val vaultClient: VaultClient
) {
    @Bean
    open fun signQueryFunction(): SignQueryFunction = f2Function { query ->
        val signer = getSigner(query.method, query.privateKey)
            ?: return@f2Function SignQueryResult(VerifiableJsonLd(emptyMap())) // Key Or Signer errors
        val jsonLd = sign(query.jsonLd, signer, query.pathToVerificationKey)
        SignQueryResult(jsonLd)
    }
    private fun sign(
        jsonLdObject: JsonLdObject,
        signer: Signer?,
        pathToVerificationKey: String
    ): VerifiableJsonLd {
        val created = LocalDateTime.now()
        val nonce: String = UUID.randomUUID().toString()

        val proofBuilder = LdProofBuilder.builder()
            .withCreated(created)
            .withChallenge(nonce)
            .withProofPurpose("assertionMethod")
            .withVerificationMethod(pathToVerificationKey) // put dynamic path

        val ldSigner = RsaSignature2018LdProofSigner(signer, proofBuilder)
        val builder = VerifiableJsonLdBuilder.builder(jsonLdObject.asJson())
        val proof = ldSigner.sign(builder)
        return builder.build(proof)
    }

    private fun getSigner(method: String, privateKey: String): Signer? {
        return when (method) {
            "rsa" -> RS256Signer(parseRSAPrivateKey(privateKey))
            "transit" -> VaultTransitSigner(privateKey, vaultClient)
            else -> {
                println("Signer type not found")
                return null
            }
        }
    }

//    private fun getRsaPrivateKey(keyName: String): RSAPrivateKey {
//        val pair = loadKeyPair("$RSA_KEYS_DIRECTORY/$keyName")
//        return pair.private as RSAPrivateKey
//    }

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
