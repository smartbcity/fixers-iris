package city.smartb.iris.signer.core.features.key

import city.smartb.iris.crypto.rsa.RSAKeyPairEncoderBase64
import city.smartb.iris.crypto.rsa.RSAKeyPairGenerator
import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.vault.core.VaultOperations
import org.springframework.vault.support.VaultResponse

typealias VaultCreateKeyCommandFunction = F2Function<VaultCreateKeyCommand, VaultCreateKeyCommandResult>

class VaultCreateKeyCommand(
    val name: String,
)

class VaultCreateKeyCommandResult(
    val vaultRawResponse: VaultResponse?,
)

@Configuration
open class VaultCreateKeyCommandFunctionImpl(
    private val vaultOperations: VaultOperations,
) {
    @Bean
    open fun vaultCreateKeyCommandFunction(): VaultCreateKeyCommandFunction = f2Function { query ->
        val keys = RSAKeyPairGenerator.generate2048Pair()

        val pubKey = RSAKeyPairEncoderBase64.encodePublicKey(keys.public as RSAPublicKey)
        val privKey = RSAKeyPairEncoderBase64.encodePrivateKey(keys.private as RSAPrivateKey)

        val map = mapOf("pubKey" to pubKey, "privKey" to privKey)

        val response = vaultOperations.write("secret/data/${query.name}", mapOf("data" to map))

        VaultCreateKeyCommandResult(response)
    }
}
