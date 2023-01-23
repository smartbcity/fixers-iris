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

/**
 * @d2 service
 * @parent [city.smartb.iris.vault.domain.D2IrisVaultPage]
 */
@RestController
@RequestMapping
@Configuration
open class IrisVaultEndpoint(
    private val irisVaultFeatures: IrisVaultFeaturesImpl
) {
    /**
     * Create a new DID Document with a new transit key
     */
    @Bean
    open fun didCreate(): DidCreateFunction = irisVaultFeatures.didCreate()

    /**
     * Add a new transit key to the specified DID
     */
    @Bean
    open fun didPublicKeyAdd(): DidPublicKeyAddFunction = irisVaultFeatures.didPublicKeyAdd()

    /**
     * Fetch a DID Document
     */
    @Bean
    open fun didGet(): DidGetFunction = irisVaultFeatures.didGet()

    /**
     * Fetch all DID Documents
     */
    @Bean
    open fun didList(): DidListFunction = irisVaultFeatures.didList()

    /**
     * Sign any json-LD with the specified transit key
     */
    @Bean
    open fun sign(): SignFunction = irisVaultFeatures.sign()

    /**
     * Verify any verifiable json-LD
     */
    @Bean
    open fun verify(): VerifyFunction = irisVaultFeatures.verify()

}
