package city.smartb.iris.crypto.hc.vault.kv.verifier

import city.smartb.iris.crypto.dsl.verifier.Verifier
import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64
import city.smartb.iris.crypto.rsa.exception.InvalidRsaKeyException
import city.smartb.iris.crypto.rsa.verifier.RS256Verifier
import city.smartb.iris.crypto.hc.vault.utils.getPublicKey
import com.nimbusds.jose.JWSAlgorithm
import java.security.interfaces.RSAPublicKey
import org.springframework.vault.core.VaultOperations

class VaultKvVerifier(
    private val vaultKeyName: String,
    private val vaultOperations: VaultOperations
): Verifier {

    override val algorithm: JWSAlgorithm
        get() = JWSAlgorithm.RS256

    override fun verify(content: ByteArray, signature: ByteArray): Boolean {
        return RS256Verifier(getPublicKey(vaultKeyName)).verify(content, signature)
    }

    private fun getPublicKey(vaultKvKeyName: String): RSAPublicKey {
        val response = vaultOperations.read("secret/data/$vaultKvKeyName")
            ?: throw InvalidRsaKeyException("Vault key not found")

        return RSAKeyPairDecoderBase64.decodePublicKey(response.getPublicKey())
    }
}
