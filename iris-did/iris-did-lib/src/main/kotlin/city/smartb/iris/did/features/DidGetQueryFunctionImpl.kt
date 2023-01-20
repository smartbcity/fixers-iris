//package city.smartb.iris.did.features
//
//import city.smartb.iris.did.DIDDocumentBuilder
//import city.smartb.iris.did.config.SsmConfig
//import city.smartb.iris.did.utils.parseTo
//import city.smartb.iris.did.domain.DidState
//import city.smartb.iris.did.domain.queries.DidGetEvent
//import city.smartb.iris.did.domain.queries.DidGetQuery
//import city.smartb.iris.did.domain.queries.DidGetQueryFunction
//import f2.dsl.fnc.f2Function
//import f2.dsl.fnc.invoke
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import ssm.chaincode.dsl.model.SessionName
//import ssm.chaincode.dsl.model.SsmSessionState
//import ssm.chaincode.dsl.query.SsmGetSessionQuery
//import ssm.chaincode.dsl.query.SsmGetSessionQueryFunction
//
//@Configuration
//open class DidGetQueryFunctionImpl(
//    private val ssmGetSessionQueryFunction: SsmGetSessionQueryFunction,
//    private val ssmConfig: SsmConfig
//) {
//
//    @Bean
//    open fun didGetQueryFunction(): DidGetQueryFunction = f2Function { cmd ->
//        getdDidDocument(cmd)
//    }
//
//    private suspend fun getdDidDocument(cmd: DidGetQuery): DidGetEvent {
//
//        val session = getSession(cmd.id)
//        val documentAsMap = session!!.public.toString().parseTo(LinkedHashMap::class.java)["document"] as LinkedHashMap<String, Any>
//        val document = DIDDocumentBuilder.fromMap(documentAsMap)
//
//        return DidGetEvent(
//            id = cmd.id,
//            type = DidState.Created(),
//            document = document.asJson()
//        )
//    }
//
//    private suspend fun getSession(sessionName: SessionName): SsmSessionState? {
//        val query = SsmGetSessionQuery(
//            chaincodeUri = ssmConfig.getChaincodeUri(),
//            sessionName = sessionName
//        )
//        return ssmGetSessionQueryFunction.invoke(query).item
//    }
//}
