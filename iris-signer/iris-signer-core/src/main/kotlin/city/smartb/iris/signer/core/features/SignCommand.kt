package city.smartb.iris.signer.core.features

import city.smartb.iris.crypto.dsl.signer.Signer
import city.smartb.iris.crypto.hc.vault.kv.signer.VaultKvSigner
import city.smartb.iris.crypto.hc.vault.transit.signer.VaultTransitSigner
import city.smartb.iris.crypto.rsa.RSAKeyPairReader.loadKeyPair
import city.smartb.iris.crypto.rsa.signer.RS256Signer
import city.smartb.iris.signer.core.signer.VerifiableCredentialSigner
import city.smartb.iris.signer.core.utils.RSA_KEYS_DIRECTORY
import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import java.security.interfaces.RSAPrivateKey
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.vault.core.VaultOperations

typealias SignCommandFunction = F2Function<SignCommand, SignCommandResult>

class SignCommand(
    val keyName: String,
    val issuer: String,
    val subject: Any,
    val id: String,
    val type: String,
)

class SignCommandResult(
    val vc: Any?,
)

@Configuration
open class SignCommandFunctionImpl(
    private val vaultOperations: VaultOperations,
) {

    @Bean
    open fun signCommandFunction(): SignCommandFunction = f2Function { query ->
        val signer = getSigner(query.type, query.keyName)
            ?: return@f2Function SignCommandResult(null) // Key Or Signer errors

        val vc = VerifiableCredentialSigner.sign(query.id, query.issuer, query.subject, signer, query.type, query.keyName)

        SignCommandResult(vc.asJson())
    }

    private fun getSigner(type: String, key: String): Signer? {
        return when (type) {
            "VaultKV" -> VaultKvSigner(key, vaultOperations)
            "RsaKey" -> RS256Signer(getRsaPrivateKey(key))
            "VaultTransit" -> VaultTransitSigner(key, vaultOperations)
            else -> {
                println("Signer type not found")
                return null
            }
        }
    }

    private fun getRsaPrivateKey(keyName: String): RSAPrivateKey {
        val pair = loadKeyPair("$RSA_KEYS_DIRECTORY/$keyName")
        return pair.private as RSAPrivateKey
    }
}
