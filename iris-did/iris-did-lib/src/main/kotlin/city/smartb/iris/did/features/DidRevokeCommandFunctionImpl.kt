//package city.smartb.iris.did.features
//
//import city.smartb.iris.s2.config.DidS2Aggregate
//import city.smartb.iris.did.domain.DidState
//import city.smartb.iris.did.domain.commands.DidRevokeCommand
//import city.smartb.iris.did.domain.commands.DidRevokeCommandFunction
//import city.smartb.iris.did.domain.commands.DidRevokedEvent
//import f2.dsl.fnc.f2Function
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
//open class DidRevokeCommandFunctionImpl(
//    private val didS2Aggregate: DidS2Aggregate
//) {
//
//    @Bean
//    open fun didRevokeCommandFunction(): DidRevokeCommandFunction = f2Function { cmd ->
//        revokeDid(cmd)
//    }
//
//    private suspend fun revokeDid(cmd: DidRevokeCommand): DidRevokedEvent = didS2Aggregate.doTransition(cmd) {
//        this to DidRevokedEvent(
//            id = cmd.id,
//            type = DidState.Revoked()
//        )
//    }
//}
