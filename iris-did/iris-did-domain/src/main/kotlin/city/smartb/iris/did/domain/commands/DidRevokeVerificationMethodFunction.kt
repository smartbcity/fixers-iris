package city.smartb.iris.did.domain.commands

import city.smartb.iris.did.domain.DidCommand
import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable

/**
 * Remove a verification method from a DID Document.
 * NOT TESTED YET
 * @d2 function
 * @parent [city.smartb.iris.did.domain.D2DidPage]
 * @order 30
 */
typealias DidRevokeVerificationMethodCommandFunction = F2Function<DidRevokeVerificationMethodCommand, DidRevokedVerificationMethodEvent>

interface DidRevokeVerificationMethodCommandPayload {
	val id: DidId
    val keyId: String
}

/**
 * @parent [DidRevokeVerificationMethodCommandFunction]
 * @d2 command
 */
@Serializable
class DidRevokeVerificationMethodCommand(
    /**
     * The DID to remove the verification method to.
     */
    override val id: DidId,

    /**
     * The ID of the verification method to remove.
     */
    override val keyId: String
) : DidCommand, DidRevokeVerificationMethodCommandPayload

@Serializable
class DidRevokedVerificationMethodEvent(
    /**
     * The current state of the DID.
     */
    override val type: DidState,

    /**
     * The DID that has been updated.
     */
    override val id: DidId,
) : DidEvent
