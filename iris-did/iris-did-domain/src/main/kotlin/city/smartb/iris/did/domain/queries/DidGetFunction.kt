package city.smartb.iris.did.domain.queries

import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual

typealias DidGetQueryFunction = F2Function<DidGetQuery, DidGetEvent>

interface DidGetQueryPayload {
	val id: DidId
}

class DidGetQuery(
    override val id: DidId
) : DidGetQueryPayload

class DidGetEvent(
    override val id: DidId,
    override val type: DidState,
    val document: Map<String, @Contextual Any>
) : DidEvent
