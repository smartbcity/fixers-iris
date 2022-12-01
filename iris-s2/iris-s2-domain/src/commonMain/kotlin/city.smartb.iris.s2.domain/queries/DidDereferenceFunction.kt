package city.smartb.iris.s2.domain.queries

import city.smartb.iris.s2.domain.DidEvent
import city.smartb.iris.s2.domain.DidId
import city.smartb.iris.s2.domain.DidState
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias DidDereferenceQueryFunction = F2Function<DidDereferenceQuery, DidDereferenceEvent>

@JsName("DidDereferenceQueryPayload")
interface DidDereferenceQueryPayload {
	val id: DidId
}

@Serializable
@JsExport
@JsName("DidDereferenceQuery")
class DidDereferenceQuery(
    override val id: DidId
) : DidDereferenceQueryPayload

@Serializable
@JsExport
@JsName("DidDereferenceEvent")
class DidDereferenceEvent(
    override val id: DidId,
    override val type: DidState,
    val document: Map<String, @Contextual Any>
) : DidEvent
