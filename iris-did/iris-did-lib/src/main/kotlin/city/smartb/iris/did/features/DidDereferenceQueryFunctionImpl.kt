//package city.smartb.iris.did.features
//
//import city.smartb.iris.jsonld.JsonLdObject
//import city.smartb.iris.did.domain.DidState
//import city.smartb.iris.did.domain.queries.DidDereferenceEvent
//import city.smartb.iris.did.domain.queries.DidDereferenceQuery
//import city.smartb.iris.did.domain.queries.DidDereferenceQueryFunction
//import city.smartb.iris.did.domain.queries.DidGetQuery
//import city.smartb.iris.did.domain.queries.DidGetQueryFunction
//import f2.dsl.fnc.f2Function
//import f2.dsl.fnc.invoke
//import java.net.URI
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
//open class DidDereferenceQueryFunctionImpl(
//    private val didGetQueryFunction: DidGetQueryFunction
//){
//
//    @Bean
//    open fun didDereferenceQueryFunction(): DidDereferenceQueryFunction = f2Function { cmd ->
//        dereferenceDidDocument(cmd)
//    }
//
//    private suspend fun dereferenceDidDocument(cmd: DidDereferenceQuery): DidDereferenceEvent {
//        val baseUri = URI.create(cmd.id)
//
//        val document = JsonLdObject(didGetQueryFunction.invoke(DidGetQuery(cmd.id)).document)
//
////        JsonLdDereferencer.findByIdInJsonLdObject(document, )
//
//        return DidDereferenceEvent(
//            id = cmd.id,
//            type = DidState.Created(),
//            document = document.asJson()
//        )
//    }
//}
