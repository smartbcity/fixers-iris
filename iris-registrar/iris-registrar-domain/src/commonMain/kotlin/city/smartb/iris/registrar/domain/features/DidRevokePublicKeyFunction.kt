package city.smartb.iris.registrar.domain.features

import f2.dsl.fnc.F2Function
import city.smartb.iris.registrar.domain.DidCommand
import city.smartb.iris.registrar.domain.DidEvent
import city.smartb.iris.registrar.domain.DidId
import city.smartb.iris.registrar.domain.DidState
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

typealias DidRevokePublicKeyCommandFunction = F2Function<DidRevokePublicKeyCommand, DidRevokedPublicKeyEvent>

// @JsExport
@JsName("DidRevokePublicKeyCommandPayload")
interface DidRevokePublicKeyCommandPayload {
	val id: DidId
}

@Serializable
// @JsExport
@JsName("DidRevokePublicKeyCommand")
class DidRevokePublicKeyCommand(
    override val id: DidId,
) : DidCommand, DidRevokePublicKeyCommandPayload

@Serializable
@JsExport
@JsName("DidRevokedPublicKeyFncEvent")
class DidRevokedPublicKeyEvent(
    override val type: DidState,
    override val id: DidId,
) : DidEvent
