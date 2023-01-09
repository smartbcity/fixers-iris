package city.smartb.iris.vault.lib

import city.smartb.iris.vault.domain.commands.DidCreateFunction
import city.smartb.iris.vault.domain.commands.DidPublicKeyAddFunction
import city.smartb.iris.vault.domain.queries.SignFunction
import city.smartb.iris.vault.domain.queries.VerifyFunction
import city.smartb.iris.vault.lib.service.IrisVaultAggregateService
import city.smartb.iris.vault.lib.service.IrisVaultFinderService
import f2.dsl.fnc.f2Function
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger

@Service
class IrisVaultFeaturesImpl(
    private val irisVaultAggregateService: IrisVaultAggregateService,
    private val irisVaultFinderService: IrisVaultFinderService
) {
    private val logger by Logger()

    /**
     * Create a new did document
     */
    fun didCreate(): DidCreateFunction = f2Function { cmd ->
        logger.debug("didCreate: $cmd")
        irisVaultAggregateService.didCreate(cmd)
    }

    fun didPublicKeyAdd(): DidPublicKeyAddFunction = f2Function { cmd ->
        logger.debug("didPublicKeyAdd: $cmd")
        irisVaultAggregateService.didPublicKeyAdd(cmd)
    }

    fun verify(): VerifyFunction = f2Function { query ->
        logger.debug("verify: $query")
        irisVaultFinderService.verify(query)
    }

    fun sign(): SignFunction = f2Function { query ->
        logger.debug("sign: $query")
        irisVaultFinderService.sign(query)
    }
}
