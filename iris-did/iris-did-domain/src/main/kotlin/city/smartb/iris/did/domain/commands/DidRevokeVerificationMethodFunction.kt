package city.smartb.iris.did.domain.commands

import city.smartb.iris.did.domain.DidCommand
import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable

typealias DidRevokeVerificationMethodCommandFunction = F2Function<DidRevokeVerificationMethodCommand, DidRevokedVerificationMethodEvent>

interface DidRevokeVerificationMethodCommandPayload {
	val id: DidId
    val keyId: String
}

@Serializable
class DidRevokeVerificationMethodCommand(
    override val id: DidId,
    override val keyId: String
) : DidCommand, DidRevokeVerificationMethodCommandPayload

@Serializable
class DidRevokedVerificationMethodEvent(
    override val type: DidState,
    override val id: DidId,
) : DidEvent
