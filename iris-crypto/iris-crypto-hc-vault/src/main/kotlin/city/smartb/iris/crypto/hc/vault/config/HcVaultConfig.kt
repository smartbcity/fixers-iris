package city.smartb.iris.crypto.hc.vault.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.vault.authentication.ClientAuthentication
import org.springframework.vault.authentication.TokenAuthentication
import org.springframework.vault.client.VaultEndpoint
import org.springframework.vault.config.AbstractVaultConfiguration

@Configuration
open class HcVaultConfig : AbstractVaultConfiguration() {

    @Value("\${vault.scheme}")
    lateinit var scheme: String

    @Value("\${vault.host}")
    lateinit var host: String

    @Value("\${vault.port}")
    lateinit var port: String

    @Value("\${vault.token}")
    lateinit var token: String

    /**
     * Specify an endpoint for connecting to Vault.
     */
    override fun vaultEndpoint(): VaultEndpoint {
        val vault = VaultEndpoint()
        vault.scheme = scheme
        vault.host = host
        vault.port = port.toInt()
        return vault
    }

    /**
     * Configure a client authentication.
     * Please consider a more secure authentication method
     * for production use.
     */
    override fun clientAuthentication(): ClientAuthentication {
        return TokenAuthentication(token)
    }
}
