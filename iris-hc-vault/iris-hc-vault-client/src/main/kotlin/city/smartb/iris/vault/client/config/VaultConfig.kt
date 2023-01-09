package city.smartb.iris.vault.client.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
open class VaultConfig{

    @Value("\${vault.baseUrl}")
    lateinit var baseUrl: String
}
