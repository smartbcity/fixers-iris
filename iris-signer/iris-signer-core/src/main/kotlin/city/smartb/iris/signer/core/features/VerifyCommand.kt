package city.smartb.iris.signer.core.features

import city.smartb.iris.crypto.dsl.verifier.Verifier
import city.smartb.iris.crypto.hc.vault.kv.verifier.VaultKvVerifier
import city.smartb.iris.crypto.hc.vault.transit.verifier.VaultTransitVerifier
import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64
import city.smartb.iris.crypto.rsa.utils.FileUtils
import city.smartb.iris.crypto.rsa.verifier.RS256Verifier
import city.smartb.iris.signer.core.utils.RSA_KEYS_DIRECTORY
import city.smartb.iris.vc.VerifiableCredential
import city.smartb.iris.vc.signer.VCVerifier
import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import java.security.interfaces.RSAPublicKey
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.vault.core.VaultOperations

typealias VerifyCommandFunction = F2Function<VerifyCommand, VerifyCommandResult>

class VerifyCommand(
    val keyName: String,
    val vc: VerifiableCredential,
    val type: String
)

class VerifyCommandResult(
    val verified: Boolean?
)

@Configuration
open class VerifyCommandFunctionImpl(
    private val vaultOperations: VaultOperations
) {

    @Bean
    open fun verifyCommandFunction(): VerifyCommandFunction = f2Function { query ->
        val verifier = getVerifier(query.type, query.keyName)
            ?: return@f2Function VerifyCommandResult(null) // Key Or Verifier errors

        val verified = VCVerifier().verify(query.vc, verifier)

        VerifyCommandResult(verified)
    }

    private fun getVerifier(type: String, key: String): Verifier? {
        return when (type) {
            "VaultKV" -> VaultKvVerifier(key, vaultOperations)
            "RsaKey" -> RS256Verifier(getRsaPublicKey(key))
            "VaultTransit" -> VaultTransitVerifier(key, vaultOperations)
            else -> {
                println("Verifier type not found")
                return null
            }
        }
    }

    private fun getRsaPublicKey(keyName: String): RSAPublicKey {
        return RSAKeyPairDecoderBase64.decodePublicKey(FileUtils.getFile("$RSA_KEYS_DIRECTORY/$keyName.pub").readText())
    }
}
