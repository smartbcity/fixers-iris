package city.smartb.iris.registrar.domain.features

import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable
import city.smartb.iris.registrar.domain.DidCommand
import city.smartb.iris.registrar.domain.DidEvent
import city.smartb.iris.registrar.domain.DidId
import city.smartb.iris.registrar.domain.DidState
import kotlin.js.JsExport
import kotlin.js.JsName

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
