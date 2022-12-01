package city.smartb.iris.signer.core.features

import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64
import city.smartb.iris.crypto.rsa.RSAKeyPairEncoderBase64
import city.smartb.iris.crypto.rsa.RSAKeyPairGenerator
import city.smartb.iris.crypto.rsa.utils.FileUtils
import city.smartb.iris.signer.core.utils.RESOURCES_PATH
import city.smartb.iris.signer.core.utils.RSA_KEYS_DIRECTORY
import city.smartb.iris.signer.domain.features.CreateKeyCommand
import city.smartb.iris.signer.domain.features.CreateKeyCommandFunction
import city.smartb.iris.signer.domain.features.CreateKeyCommandResult
import city.smartb.iris.signer.domain.features.GenerateRsaVaultKeyCommandFunction
import city.smartb.iris.signer.domain.features.GenerateRsaVaultKeyCommandResult
import city.smartb.iris.signer.domain.features.GetKeyQuery
import city.smartb.iris.signer.domain.features.GetKeyQueryFunction
import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.vault.core.VaultOperations

@Configuration
open class GenerateRsaVaultKeyCommandFunctionImpl(
    private val vaultOperations: VaultOperations,
    private val createKeyCommandFunction: CreateKeyCommandFunction,
    private val getKeyQueryFunction: GetKeyQueryFunction,
) {
    @Bean
    open fun generateRsaVaultKeyCommandFunction(): GenerateRsaVaultKeyCommandFunction = f2Function { cmd ->
        val createCommand = CreateKeyCommand(
            keyName = cmd.keyId,
            type = "HcVaultKV"
        )
        createKeyCommandFunction.invoke(createCommand)

        val getQuery = GetKeyQuery(
            keyName = cmd.keyId,
            type = "HcVaultKV"
        )
        val response = getKeyQueryFunction.invoke(getQuery)

        GenerateRsaVaultKeyCommandResult(
            keyId = cmd.keyId,
            publicKey = RSAKeyPairDecoderBase64.decodePublicKey(response.publicKey)
        )
    }
}
