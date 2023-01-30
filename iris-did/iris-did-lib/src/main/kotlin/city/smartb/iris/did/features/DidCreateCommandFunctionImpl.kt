//package city.smartb.iris.did.features
//
//import city.smartb.iris.did.DIDDocument
//import city.smartb.iris.did.DIDDocumentBuilder
//import city.smartb.iris.did.signer.DidSigner
//import city.smartb.iris.s2.config.DidS2Aggregate
//import city.smartb.iris.did.domain.DidDocumentBase
//import city.smartb.iris.did.domain.DidState
//import city.smartb.iris.did.domain.commands.DidCreateCommand
//import city.smartb.iris.did.domain.commands.DidCreateCommandFunction
//import city.smartb.iris.did.domain.commands.DidCreatedEvent
//import city.smartb.iris.s2.entity.DidEntity
//import f2.dsl.fnc.f2Function
//import java.util.UUID
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
//open class DidCreateCommandFunctionImpl(
//    private val didSigner: DidSigner,
//    private val didS2Aggregate: DidS2Aggregate
//) {
//
//    open fun didCreateCommandFunction(): DidCreateCommandFunction = f2Function { cmd ->
//        createDid(cmd)
//    }
//
//    private suspend fun createDid(cmd: DidCreateCommand): DidCreatedEvent {
//        val document = createDidDocument()
//
//        return didS2Aggregate.createWithEvent(cmd,
//            { DidCreatedEvent(s2Id(), DidState(this.state), document) }
//        ) {
//            DidEntity(document = document, state = DidState.Created().position)
//        }
//    }
//
//    private fun createDidDocument(): DidDocumentBase {
//        val did = generateDID()
//        return DidDocumentBase(
//            id = did,
//            controller = did
//        )
//    }
//
////    private suspend fun createDIDDocument(): DIDDocument {
////        val did = generateDID()
////
////        val vcBuild = DIDDocumentBuilder
////            .create()
////            .withId(did)
////            .withController(did)
////            .withServices(emptyList())
////            .withVerificationMethods(emptyList())
////            .withAuthentications(emptyList())
////            .withCapabilityDelegations(emptyList())
////            .withCapabilityInvocations(emptyList())
////            .withAssertionMethods(emptyList())
////            .withKeyAgreements(emptyList())
////
////        val didDocument = DIDDocument(vcBuild.asJson())
////
////        return didSigner.sign(didDocument)
////    }
//
//
//
//    private fun generateDID(): String {
//        return "did:smartb:${UUID.randomUUID()}"
//    }
//}
