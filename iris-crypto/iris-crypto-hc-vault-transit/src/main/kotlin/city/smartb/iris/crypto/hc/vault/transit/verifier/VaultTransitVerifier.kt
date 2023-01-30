package city.smartb.iris.crypto.hc.vault.transit.verifier

import city.smartb.iris.crypto.dsl.verifier.Verifier
import city.smartb.iris.vault.client.VaultClient
import city.smartb.iris.vault.domain.commands.TransitVerifyCommand
import com.nimbusds.jose.JWSAlgorithm
import java.util.Base64
import kotlinx.coroutines.runBlocking

class VaultTransitVerifier(
    private val vaultKeyName: String,
    private val vaultClient: VaultClient
): Verifier {

    override val algorithm: JWSAlgorithm?
        get() = JWSAlgorithm.RS256

    override fun verify(content: ByteArray, signature: ByteArray): Boolean = runBlocking {
        vaultClient.transitVerify(TransitVerifyCommand(
            keyName = vaultKeyName,
            input = Base64.getEncoder().encodeToString(content),
            signature = "vault:v1:${Base64.getEncoder().encodeToString(signature)}"
        )).isValid
    }
}
