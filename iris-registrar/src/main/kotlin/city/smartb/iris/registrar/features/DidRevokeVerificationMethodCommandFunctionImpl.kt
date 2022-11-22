package city.smartb.iris.registrar.features

import city.smartb.iris.registrar.signer.IrisRegistrarSigner
import city.smartb.iris.s2.config.DidS2Aggregate
import city.smartb.iris.s2.domain.DidState
import city.smartb.iris.s2.domain.commands.DidRevokeVerificationMethodCommand
import city.smartb.iris.s2.domain.commands.DidRevokeVerificationMethodCommandFunction
import city.smartb.iris.s2.domain.commands.DidRevokedVerificationMethodEvent
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class DidRevokeVerificationMethodCommandFunctionImpl(
    private val didS2Aggregate: DidS2Aggregate
) {

    @Bean
    open fun didRevokeVerificationMethodCommandFunction():
            DidRevokeVerificationMethodCommandFunction = f2Function { cmd ->
        revokeVerificationMethod(cmd)
    }

    private suspend fun revokeVerificationMethod(cmd: DidRevokeVerificationMethodCommand):
            DidRevokedVerificationMethodEvent = didS2Aggregate.doTransition(cmd) {
        this.document.removeVerificationMethod(cmd.keyId)
        this.document = IrisRegistrarSigner().renewRegistrarProof(this.document)
        this to DidRevokedVerificationMethodEvent(
            id = cmd.id,
            type = DidState(this.state)
        )
    }
}
