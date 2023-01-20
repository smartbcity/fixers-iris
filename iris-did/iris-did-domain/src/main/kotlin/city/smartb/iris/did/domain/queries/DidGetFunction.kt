package city.smartb.iris.did.domain.queries

import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.F2Function

typealias DidGetFunction = F2Function<DidGetQuery, DidGetEvent>

class DidGetQuery(
    val id: DidId
)

class DidGetEvent(
    override val id: DidId,
    override val type: DidState,
    val document: DIDDocument
) : DidEvent
