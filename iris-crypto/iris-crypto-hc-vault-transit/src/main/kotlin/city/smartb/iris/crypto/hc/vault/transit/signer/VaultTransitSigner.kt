package city.smartb.iris.crypto.hc.vault.transit.signer

import city.smartb.iris.crypto.dsl.signer.Signer
import city.smartb.iris.crypto.hc.vault.utils.getSignatureValue
import com.nimbusds.jose.JWSAlgorithm
import java.util.Base64
import org.springframework.vault.core.VaultOperations

class VaultTransitSigner(
    private val vaultKeyName: String,
    private val vaultOperations: VaultOperations
): Signer {

    override val algorithm: JWSAlgorithm
        get() = JWSAlgorithm.RS256
    override val term: String
        get() = "RsaSignature2018"

    override fun sign(content: ByteArray): ByteArray {
        val requestPayload = mapOf(
            "input" to Base64.getEncoder().encodeToString(content),
            "signature_algorithm" to "pkcs1v15",
            "prehashed" to false,
        )
        val response = vaultOperations.write("transit/sign/$vaultKeyName/sha2-256", requestPayload)
        val signature = response!!.getSignatureValue()

        return signature.toByteArray()
    }
}
