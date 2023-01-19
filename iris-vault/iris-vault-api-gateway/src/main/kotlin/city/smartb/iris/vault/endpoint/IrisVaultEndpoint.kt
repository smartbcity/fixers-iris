package city.smartb.iris.vault.endpoint

import city.smartb.iris.vault.domain.commands.DidCreateFunction
import city.smartb.iris.vault.domain.commands.DidPublicKeyAddFunction
import city.smartb.iris.vault.domain.queries.DidGetFunction
import city.smartb.iris.vault.domain.queries.DidListFunction
import city.smartb.iris.vault.domain.queries.SignFunction
import city.smartb.iris.vault.domain.queries.VerifyFunction
import city.smartb.iris.vault.lib.IrisVaultFeaturesImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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

    @Bean
    open fun didGet(): DidGetFunction = irisVaultFeatures.didGet()

    @Bean
    open fun didList(): DidListFunction = irisVaultFeatures.didList()

    @Bean
    open fun sign(): SignFunction = irisVaultFeatures.sign()

    @Bean
    open fun verify(): VerifyFunction = irisVaultFeatures.verify()

}
