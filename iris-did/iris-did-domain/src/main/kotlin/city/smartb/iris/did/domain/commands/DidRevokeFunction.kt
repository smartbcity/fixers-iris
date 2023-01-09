package city.smartb.iris.did.domain.commands

import city.smartb.iris.did.domain.DidCommand
import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable

typealias DidRevokeFunction = F2Function<DidRevokeCommand, DidRevokedEvent>

interface DidRevokePayload {
	val id: DidId
}

@Serializable
class DidRevokeCommand(
    override val id: DidId,
) : DidCommand, DidRevokePayload

@Serializable
class DidRevokedEvent(
    override val id: DidId,
    override val type: DidState,
) : DidEvent
