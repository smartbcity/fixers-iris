package city.smartb.iris.did.service

import city.smartb.iris.did.DIDDocument
import city.smartb.iris.did.config.SsmConfig
import city.smartb.iris.did.domain.DidState
import city.smartb.iris.did.domain.queries.DidGetEvent
import city.smartb.iris.did.domain.queries.DidGetQuery
import city.smartb.iris.resolver.utils.parseTo
import f2.dsl.fnc.invoke
import org.springframework.stereotype.Service
import ssm.chaincode.dsl.model.SessionName
import ssm.chaincode.dsl.model.SsmSessionState
import ssm.chaincode.dsl.query.SsmGetSessionQuery
import ssm.chaincode.dsl.query.SsmGetSessionQueryFunction

@Service
class DidFinderService(
    private val ssmGetSessionQueryFunction: SsmGetSessionQueryFunction,
    private val ssmConfig: SsmConfig
) {
    suspend fun get(query: DidGetQuery): DidGetEvent {
        return getDidDocument(query)
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
