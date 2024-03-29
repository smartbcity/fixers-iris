package city.smartb.iris.did

import city.smartb.iris.did.domain.commands.DidCreateFunction
import city.smartb.iris.did.domain.commands.DidProofUpdateFunction
import city.smartb.iris.did.domain.commands.DidUpdateFunction
import city.smartb.iris.did.domain.commands.DidVerificationMethodAddFunction
import city.smartb.iris.did.domain.queries.DidGetFunction
import city.smartb.iris.did.domain.queries.DidListFunction
import city.smartb.iris.did.service.DidAggregateService
import city.smartb.iris.did.service.DidFinderService
import f2.dsl.fnc.f2Function
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger

@Service
class DidFeaturesImpl(
    private val didAggregateService: DidAggregateService,
    private val didFinderService: DidFinderService
) {
    private val logger by Logger()

    /**
     * Create a new did document
     */
    fun didCreate(): DidCreateFunction = f2Function { cmd ->
        logger.debug("didCreate: $cmd")
        didAggregateService.create(cmd)
    }

    fun didVerificationMethodAdd(): DidVerificationMethodAddFunction = f2Function { cmd ->
        logger.debug("didVerificationMethodAdd: $cmd")
        didAggregateService.verificationMethodAdd(cmd)
    }

    fun didUpdate(): DidUpdateFunction = f2Function { cmd ->
        logger.debug("didUpdate: $cmd")
        didAggregateService.update(cmd)
    }

    fun didProofUpdate(): DidProofUpdateFunction = f2Function { cmd ->
        logger.debug("didProofUpdate: $cmd")
        didAggregateService.updateProof(cmd)
    }

    fun didGet(): DidGetFunction = f2Function { query ->
        logger.debug("didGet: $query")
        didFinderService.get(query)
    }

    fun didList(): DidListFunction = f2Function { query ->
        logger.debug("didList: $query")
        didFinderService.list(query)
    }
}
