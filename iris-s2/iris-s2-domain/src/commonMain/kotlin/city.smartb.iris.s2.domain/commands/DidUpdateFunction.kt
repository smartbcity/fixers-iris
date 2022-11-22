package city.smartb.iris.s2.domain.commands

import city.smartb.iris.s2.domain.DidCommand
import city.smartb.iris.s2.domain.DidEvent
import city.smartb.iris.s2.domain.DidId
import city.smartb.iris.s2.domain.DidState
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias DidUpdateCommandFunction = F2Function<DidUpdateCommand, DidUpdatedEvent>

// @JsExport
@JsName("DidUpdateCommandPayload")
interface DidUpdateCommandPayload {
	val id: DidId
    val document: Map<String, @Contextual Any>
}

@Serializable
@JsExport
@JsName("DidUpdateCommand")
class DidUpdateCommand(
    override val id: DidId,
    override val document: Map<String, @Contextual Any>
) : DidCommand, DidUpdateCommandPayload

@Serializable
@JsExport
@JsName("DidUpdatedEvent")
class DidUpdatedEvent(
    override val id: DidId,
    override val type: DidState,
    val document: Map<String, @Contextual Any>
) : DidEvent
