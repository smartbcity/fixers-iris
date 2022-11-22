package city.smartb.iris.registrar.features

import city.smartb.iris.did.DIDDocument
import city.smartb.iris.registrar.signer.IrisRegistrarSigner
import city.smartb.iris.s2.config.DidS2Aggregate
import city.smartb.iris.s2.domain.DidState
import city.smartb.iris.s2.domain.commands.DidUpdateCommand
import city.smartb.iris.s2.domain.commands.DidUpdateCommandFunction
import city.smartb.iris.s2.domain.commands.DidUpdatedEvent
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class DidUpdateCommandFunctionImpl(
    private val didS2Aggregate: DidS2Aggregate
) {

    @Bean
    open fun didUpdateCommandFunction(): DidUpdateCommandFunction = f2Function { cmd ->
        updateDid(cmd)
    }

    private suspend fun updateDid(cmd: DidUpdateCommand): DidUpdatedEvent = didS2Aggregate.doTransition(cmd) {
        this.document = DIDDocument(cmd.document)

        this.document = IrisRegistrarSigner().renewRegistrarProof(this.document)

        this to DidUpdatedEvent(
            id = cmd.id,
            type = DidState.Created(),
            document = cmd.document
        )
    }
}
