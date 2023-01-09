package city.smartb.iris.vault.endpoint

import city.smartb.iris.vault.domain.commands.DidCreateFunction
import city.smartb.iris.vault.domain.commands.DidPublicKeyAddFunction
import city.smartb.iris.vault.lib.IrisVaultFeaturesImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import s2.spring.utils.logger.Logger

@RestController
@RequestMapping
@Configuration
open class IrisVaultEndpoint(
    private val irisVaultFeatures: IrisVaultFeaturesImpl
) {
    @Bean
    open fun didCreate(): DidCreateFunction = irisVaultFeatures.didCreate()

    @Bean
    open fun didPublicKeyAdd(): DidPublicKeyAddFunction = irisVaultFeatures.didPublicKeyAdd()

}
