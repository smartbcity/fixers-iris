package city.smartb.iris.crypto.hc.vault.kv.signer

import city.smartb.iris.crypto.dsl.signer.Signer
import city.smartb.iris.crypto.hc.vault.utils.getPrivateKey
import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64
import city.smartb.iris.crypto.rsa.exception.InvalidRsaKeyException
import city.smartb.iris.crypto.rsa.signer.RS256Signer
import com.nimbusds.jose.JWSAlgorithm
import java.security.GeneralSecurityException
import java.security.interfaces.RSAPrivateKey
import org.springframework.vault.core.VaultOperations

class VaultKvSigner(
    private val vaultKeyName: String,
    private val vaultOperations: VaultOperations
): Signer {

    override val algorithm: JWSAlgorithm?
        get() = JWSAlgorithm.RS256
    override val term: String
        get() = "RsaSignature2018"

    @Throws(GeneralSecurityException::class)
    override fun sign(content: ByteArray): ByteArray {
        val privKey = getPrivateKey(vaultKeyName)
        return RS256Signer(privKey).sign(content)
    }

    private fun getPrivateKey(vaultKvKeyName: String): RSAPrivateKey {
        // TODO Handle private key accessibility/authentication
        val response = vaultOperations.read("secret/data/$vaultKvKeyName")
            ?: throw InvalidRsaKeyException("Vault key not found")

        return RSAKeyPairDecoderBase64.decodePrivateKey(response.getPrivateKey())
    }
}
