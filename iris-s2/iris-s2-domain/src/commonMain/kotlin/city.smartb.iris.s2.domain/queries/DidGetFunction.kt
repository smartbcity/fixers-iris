package city.smartb.iris.s2.domain.queries

import city.smartb.iris.s2.domain.DidEvent
import city.smartb.iris.s2.domain.DidId
import city.smartb.iris.s2.domain.DidState
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias DidGetQueryFunction = F2Function<DidGetQuery, DidGetEvent>

@JsName("DidGetQueryPayload")
interface DidGetQueryPayload {
	val id: DidId
}

@Serializable
@JsExport
@JsName("DidGetQuery")
class DidGetQuery(
    override val id: DidId
) : DidGetQueryPayload

@Serializable
@JsExport
@JsName("DidGetEvent")
class DidGetEvent(
    override val id: DidId,
    override val type: DidState,
    val document: Map<String, @Contextual Any>
) : DidEvent
