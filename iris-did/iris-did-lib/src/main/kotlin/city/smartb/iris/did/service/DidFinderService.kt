package city.smartb.iris.did.service

import city.smartb.iris.did.config.SsmConfig
import city.smartb.iris.did.domain.DidState
import city.smartb.iris.did.domain.queries.DidGetEvent
import city.smartb.iris.did.domain.queries.DidGetQuery
import city.smartb.iris.did.domain.queries.DidListEvent
import city.smartb.iris.did.domain.queries.DidListQuery
import city.smartb.iris.did.utils.parseTo
import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.invoke
import org.springframework.stereotype.Service
import ssm.chaincode.dsl.model.SessionName
import ssm.chaincode.dsl.model.SsmSessionState
import ssm.chaincode.dsl.query.SsmGetSessionQuery
import ssm.chaincode.dsl.query.SsmGetSessionQueryFunction
import ssm.chaincode.dsl.query.SsmListSessionQuery
import ssm.chaincode.dsl.query.SsmListSessionQueryFunction

@Service
class DidFinderService(
    private val ssmGetSessionQueryFunction: SsmGetSessionQueryFunction,
    private val ssmListSessionQueryFunction: SsmListSessionQueryFunction,
    private val ssmConfig: SsmConfig
) {
    suspend fun get(query: DidGetQuery): DidGetEvent {
        return getDidDocument(query)
    }

    suspend fun list(query: DidListQuery): DidListEvent {
        val sessionNames = ssmListSessionQueryFunction.invoke(SsmListSessionQuery(
            chaincodeUri = ssmConfig.getChaincodeUri()
        )).items

        return DidListEvent(
            documents = sessionNames.map { getDidDocument(DidGetQuery(it)).document }
        )
    }

    private suspend fun getDidDocument(cmd: DidGetQuery): DidGetEvent {

        val session = getSession(cmd.id)
        val documentAsMap = session!!.public.toString().parseTo(LinkedHashMap::class.java)["document"] as LinkedHashMap<String, Any>

        return DidGetEvent(
            id = cmd.id,
            type = DidState.Created(),
            document = DIDDocument(documentAsMap)
        )
    }

    private suspend fun getSession(sessionName: SessionName): SsmSessionState? {
        val query = SsmGetSessionQuery(
            chaincodeUri = ssmConfig.getChaincodeUri(),
            sessionName = sessionName
        )
        return ssmGetSessionQueryFunction.invoke(query).item
    }
}
