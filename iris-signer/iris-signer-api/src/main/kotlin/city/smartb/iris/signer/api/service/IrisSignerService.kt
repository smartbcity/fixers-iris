package city.smartb.iris.signer.api.service

import city.smartb.iris.signer.core.features.key.VaultCreateKeyCommandFunction
import city.smartb.iris.signer.core.features.key.VaultGetKeyQueryFunction
import city.smartb.iris.signer.core.features.transit.TransitCreateKeyCommandFunction
import city.smartb.iris.signer.core.features.transit.TransitGetKeyQueryFunction
import city.smartb.iris.signer.core.features.transit.TransitSignPayloadCommandFunction
import city.smartb.iris.signer.core.features.transit.TransitVerifyVCQueryFunction
import city.smartb.iris.signer.core.features.vc.VaultCreateVerifiableCredentialQueryFunction
import city.smartb.iris.signer.core.features.vc.VaultVerifyVerifiableCredentialQueryFunction
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class IrisSignerService(
    private val vaultCreateVerifiableCredentialQueryFunction: VaultCreateVerifiableCredentialQueryFunction,
    private val vaultCreateKeyCommandFunction: VaultCreateKeyCommandFunction,
    private val vaultGetKeyQueryFunction: VaultGetKeyQueryFunction,
    private val vaultVerifyVerifiableCredentialQueryFunction: VaultVerifyVerifiableCredentialQueryFunction,
    private val transitCreateKeyCommandFunction: TransitCreateKeyCommandFunction,
    private val transitGetKeyQueryFunction: TransitGetKeyQueryFunction,
    private val transitSignPayloadCommandFunction: TransitSignPayloadCommandFunction,
    private val transitVerifyVCQueryFunction: TransitVerifyVCQueryFunction
) {

    @Bean
    fun createKey() = vaultCreateKeyCommandFunction

    @Bean
    fun getKey() = vaultGetKeyQueryFunction

    @Bean
    fun signPayload() = vaultCreateVerifiableCredentialQueryFunction

    @Bean
    fun verifyVerifiableCredential() = vaultVerifyVerifiableCredentialQueryFunction

    @Bean
    fun createTransitKey() = transitCreateKeyCommandFunction

    @Bean
    fun getTransitKey() = transitGetKeyQueryFunction

    @Bean
    fun signTransitPayload() = transitSignPayloadCommandFunction

    @Bean
    fun verifyTransitVC() = transitVerifyVCQueryFunction
}
