package city.smartb.iris.s2.domain.commands

import city.smartb.iris.s2.domain.DidCommand
import city.smartb.iris.s2.domain.DidEvent
import city.smartb.iris.s2.domain.DidId
import city.smartb.iris.s2.domain.DidState
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

typealias DidRevokeCommandFunction = F2Function<DidRevokeCommand, DidRevokedEvent>

// @JsExport
@JsName("DidRevokeCommandPayload")
interface DidRevokeCommandPayload {
	val id: DidId
}

@Serializable
@JsExport
@JsName("DidRevokeCommand")
class DidRevokeCommand(
    override val id: DidId,
) : DidCommand, DidRevokeCommandPayload

@Serializable
@JsExport
@JsName("DidRevokedEvent")
class DidRevokedEvent(
    override val id: DidId,
    override val type: DidState,
) : DidEvent
