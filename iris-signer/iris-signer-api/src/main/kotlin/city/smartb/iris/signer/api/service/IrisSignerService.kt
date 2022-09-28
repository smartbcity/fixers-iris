package city.smartb.iris.signer.api.service

import city.smartb.iris.signer.core.features.key.VaultCreateKeyCommandFunction
import city.smartb.iris.signer.core.features.key.VaultGetKeyQueryFunction
import city.smartb.iris.signer.core.features.vc.VaultCreateVerifiableCredentialQueryFunction
import city.smartb.iris.signer.core.features.vc.VaultVerifyVerifiableCredentialQueryFunction
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class IrisSignerService(
    private val vaultCreateVerifiableCredentialQueryFunction: VaultCreateVerifiableCredentialQueryFunction,
    private val vaultCreateKeyCommandFunction: VaultCreateKeyCommandFunction,
    private val vaultGetKeyQueryFunction: VaultGetKeyQueryFunction,
    private val vaultVerifyVerifiableCredentialQueryFunction: VaultVerifyVerifiableCredentialQueryFunction
) {

    @Bean
    fun createKey() = vaultCreateKeyCommandFunction

    @Bean
    fun getKey() = vaultGetKeyQueryFunction

    @Bean
    fun signPayload() = vaultCreateVerifiableCredentialQueryFunction

    @Bean
    fun verifyVerifiableCredential() = vaultVerifyVerifiableCredentialQueryFunction
}
