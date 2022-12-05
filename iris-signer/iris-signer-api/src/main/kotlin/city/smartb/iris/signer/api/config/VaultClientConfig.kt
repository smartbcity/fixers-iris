package city.smartb.iris.signer.api.config

import city.smartb.iris.vault.client.VaultAuthClient
import city.smartb.iris.vault.client.VaultClient
import city.smartb.iris.vault.client.config.VaultConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class VaultClientConfig(
    private val vaultAuthClient: VaultAuthClient,
    private val vaultConfig: VaultConfig
){

    @Bean
    open fun vaultClient(): VaultClient {
        return VaultClient(
            vaultAuthClient::generateToken,
            vaultAuthClient::getVaultEntityId,
            vaultConfig
        )
    }
}
