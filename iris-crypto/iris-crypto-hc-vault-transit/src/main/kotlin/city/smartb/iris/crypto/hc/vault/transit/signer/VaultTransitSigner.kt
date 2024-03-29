package city.smartb.iris.crypto.hc.vault.transit.signer

import city.smartb.iris.crypto.dsl.signer.Signer
import city.smartb.iris.vault.client.VaultClient
import city.smartb.iris.vault.domain.commands.TransitSignCommand
import com.nimbusds.jose.JWSAlgorithm
import java.util.Base64

class VaultTransitSigner(
    private val vaultKeyName: String,
    private val vaultClient: VaultClient
): Signer {

    override val algorithm: JWSAlgorithm
        get() = JWSAlgorithm.RS256
    override val term: String
        get() = "RsaSignature2018"

    override suspend fun sign(content: ByteArray): ByteArray {
        val response = vaultClient.transitSign(TransitSignCommand(
            keyName = vaultKeyName,
            input = Base64.getEncoder().encodeToString(content)
        ))
        val signature = response.signature

        return Base64.getDecoder().decode(signature.removePrefix("vault:v1:"))
    }
}
