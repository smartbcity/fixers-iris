package city.smartb.iris.crypto.hc.vault.transit.verifier

import city.smartb.iris.crypto.dsl.verifier.Verifier
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.util.Base64URL
import java.util.Base64
import org.springframework.vault.core.VaultOperations

class VaultTransitVerifier(
    private val vaultKeyName: String,
    private val vaultOperations: VaultOperations
): Verifier {

    override val algorithm: JWSAlgorithm?
        get() = JWSAlgorithm.RS256

    override fun verify(content: ByteArray, signature: ByteArray): Boolean {
        val requestPayload = mapOf(
            "input" to Base64.getEncoder().encodeToString(content),
            "signature_algorithm" to "pkcs1v15",
            "prehashed" to false,
            "signature" to "vault:v1:${Base64.getEncoder().encodeToString(signature)}"
        )

        val response = vaultOperations.write("transit/verify/${vaultKeyName}/sha2-256", requestPayload)

        return response!!.data!!["valid"] as Boolean
    }
}
