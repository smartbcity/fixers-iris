package city.smartb.iris.did.domain.commands

import city.smartb.iris.did.domain.DidCommand
import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable

/**
 * Set the state of the DID Document to Revoked.
 * @d2 function
 * @parent [city.smartb.iris.did.domain.D2DidPage]
 * @order 20
 */
typealias DidRevokeFunction = F2Function<DidRevokeCommand, DidRevokedEvent>

interface DidRevokePayload {
	val id: DidId
}

/**
 * @d2 command
 * @parent [DidRevokeFunction]
 */
@Serializable
class DidRevokeCommand(
    /**
     * The DID to revoke.
     */
    override val id: DidId,
) : DidCommand, DidRevokePayload

/**
 * @d2 event
 * @parent [DidRevokeFunction]
 */
@Serializable
class DidRevokedEvent(
    /**
     * The revoked DID.
     */
    override val id: DidId,

    /**
     * The new state of the DID.
     */
    override val type: DidState,
) : DidEvent
