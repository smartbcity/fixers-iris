//package city.smartb.iris.did.features
//
//import city.smartb.iris.did.DIDDocument
//import city.smartb.iris.did.signer.DidSigner
//import city.smartb.iris.s2.config.DidS2Aggregate
//import city.smartb.iris.did.domain.DidState
//import city.smartb.iris.did.domain.commands.DidUpdateCommand
//import city.smartb.iris.did.domain.commands.DidUpdateCommandFunction
//import city.smartb.iris.did.domain.commands.DidUpdatedEvent
//import f2.dsl.fnc.f2Function
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
//open class DidUpdateCommandFunctionImpl(
//    private val didS2Aggregate: DidS2Aggregate,
//    private val didSigner: DidSigner
//) {
//
//    @Bean
//    open fun didUpdateCommandFunction(): DidUpdateCommandFunction = f2Function { cmd ->
//        updateDid(cmd)
//    }
//
//    private suspend fun updateDid(cmd: DidUpdateCommand): DidUpdatedEvent = didS2Aggregate.doTransition(cmd) {
//        this.document = DIDDocument(cmd.document)
//
//        this.document = didSigner.sign(this.document)
//
//        this to DidUpdatedEvent(
//            id = cmd.id,
//            type = DidState.Created(),
//            document = cmd.document
//        )
//    }
//}
