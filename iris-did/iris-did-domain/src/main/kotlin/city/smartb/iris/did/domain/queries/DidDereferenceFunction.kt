package city.smartb.iris.did.domain.queries

import city.smartb.iris.did.DIDDocument
import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias DidDereferenceQueryFunction = F2Function<DidDereferenceQuery, DidDereferenceEvent>

interface DidDereferenceQueryPayload {
	val id: DidId
}

class DidDereferenceQuery(
    override val id: DidId
) : DidDereferenceQueryPayload

class DidDereferenceEvent(
    override val id: DidId,
    override val type: DidState,
    val document: DIDDocument
) : DidEvent