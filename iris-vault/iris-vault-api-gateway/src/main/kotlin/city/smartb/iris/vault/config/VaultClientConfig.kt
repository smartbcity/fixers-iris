package city.smartb.iris.vault.config

import city.smartb.iris.vault.client.VaultAuthClient
import city.smartb.iris.vault.client.VaultClient
import city.smartb.iris.vault.client.config.VaultConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor

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
