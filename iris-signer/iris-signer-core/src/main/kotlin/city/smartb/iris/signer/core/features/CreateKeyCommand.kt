package city.smartb.iris.signer.core.features

import city.smartb.iris.crypto.rsa.RSAKeyPairEncoderBase64
import city.smartb.iris.crypto.rsa.RSAKeyPairGenerator
import city.smartb.iris.crypto.rsa.utils.FileUtils
import city.smartb.iris.signer.core.utils.RESOURCES_PATH
import city.smartb.iris.signer.core.utils.RSA_KEYS_DIRECTORY
import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.vault.core.VaultOperations

typealias CreateKeyCommandFunction = F2Function<CreateKeyCommand, CreateKeyCommandResult>

class CreateKeyCommand(
    val keyName: String,
    val type: String
)

class CreateKeyCommandResult

@Configuration
open class CreateKeyCommandFunctionImpl(
    private val vaultOperations: VaultOperations
) {

    @Bean
    open fun createKeyCommandFunction(): CreateKeyCommandFunction = f2Function { query ->
        when(query.type) {
            "RsaKey" -> createRsaKey(query.keyName)
            "HcVaultKV" -> createHcVaultKVKey(query.keyName)
            "HcVaultTransit" -> createHcVaultTransitKey(query.keyName)
            else -> {}
        }

        CreateKeyCommandResult()
    }

    private fun createHcVaultKVKey(keyName: String) {
        val keys = RSAKeyPairGenerator.generate2048Pair()

        val pubKey = RSAKeyPairEncoderBase64.encodePublicKey(keys.public as RSAPublicKey)
        val privKey = RSAKeyPairEncoderBase64.encodePrivateKey(keys.private as RSAPrivateKey)

        val map = mapOf("pubKey" to pubKey, "privKey" to privKey)

        vaultOperations.write("secret/data/$keyName", mapOf("data" to map))
    }

    private fun createHcVaultTransitKey(keyName: String) {
        vaultOperations.write("transit/keys/$keyName", mapOf("type" to "rsa-2048"))
    }

    private fun createRsaKey(keyName: String) {
        val keys = RSAKeyPairGenerator.generate2048Pair()

        val pubKey = RSAKeyPairEncoderBase64.encodePublicKey(keys.public as RSAPublicKey)
        val privKey = RSAKeyPairEncoderBase64.encodePrivateKey(keys.private as RSAPrivateKey)

        FileUtils.saveFile("$RESOURCES_PATH/$RSA_KEYS_DIRECTORY/$keyName.pub", pubKey, true)
        FileUtils.saveFile("$RESOURCES_PATH/$RSA_KEYS_DIRECTORY/$keyName", privKey, true)
    }
}
