package city.smartb.iris.s2.domain.commands

import city.smartb.iris.s2.domain.DidCommand
import city.smartb.iris.s2.domain.DidEvent
import city.smartb.iris.s2.domain.DidId
import city.smartb.iris.s2.domain.DidState
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

typealias DidRevokeVerificationMethodCommandFunction = F2Function<DidRevokeVerificationMethodCommand, DidRevokedVerificationMethodEvent>

// @JsExport
@JsName("DidRevokeVerificationMethodCommandPayload")
interface DidRevokeVerificationMethodCommandPayload {
	val id: DidId
    val keyId: String
}

@Serializable
// @JsExport
@JsName("DidRevokeVerificationMethodCommand")
class DidRevokeVerificationMethodCommand(
    override val id: DidId,
    override val keyId: String
) : DidCommand, DidRevokeVerificationMethodCommandPayload

@Serializable
@JsExport
@JsName("DidRevokedVerificationMethodFncEvent")
class DidRevokedVerificationMethodEvent(
    override val type: DidState,
    override val id: DidId,
) : DidEvent
