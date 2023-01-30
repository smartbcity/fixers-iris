package city.smartb.iris.keypair.lib

import city.smartb.iris.keypair.domain.KeypairCreateFunction
import city.smartb.iris.keypair.domain.PublicKeyGetFunction
import city.smartb.iris.keypair.domain.SignFunction
import city.smartb.iris.keypair.domain.VerifyFunction
import city.smartb.iris.keypair.lib.service.KeypairAggregateService
import city.smartb.iris.keypair.lib.service.KeypairFinderService
import f2.dsl.fnc.f2Function
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger

@Service
class KeypairFeaturesImpl(
    private val keypairAggregateService: KeypairAggregateService,
    private val keypairFinderService: KeypairFinderService
) {
    private val logger by Logger()

    /**
     * Create a new keypair
     */
    fun keypairCreate(): KeypairCreateFunction = f2Function { cmd ->
        logger.debug("keypairCreate: $cmd")
        keypairAggregateService.create(cmd)
    }

    fun sign(): SignFunction = f2Function { query ->
        logger.debug("sign: $query")
        keypairFinderService.sign(query)
    }

    fun verify(): VerifyFunction = f2Function { query ->
        logger.debug("verify: $query")
        keypairFinderService.verify(query)
    }

    fun publicKeyGet(): PublicKeyGetFunction = f2Function { query ->
        logger.debug("public key get: $query")
        keypairFinderService.publicKeyGet(query)
    }
}
