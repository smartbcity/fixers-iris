package city.smartb.iris.did.domain.commands

import city.smartb.iris.did.domain.DidCommand
import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import city.smartb.iris.ld.did.DIDDocument
import city.smartb.iris.ld.ldproof.LdProof
import f2.dsl.fnc.F2Function

/**
 * Replace the proof of the DID Document with the one provided.
 * @d2 function
 * @parent [city.smartb.iris.did.domain.D2DidPage]
 * @order 50
 */
typealias DidProofUpdateFunction = F2Function<DidProofUpdateCommand, DidProofUpdatedEvent>

/**
 * @d2 command
 * @parent [DidProofUpdateFunction]
 */
class DidProofUpdateCommand(
    /**
     * The DID to update the proof to.
     */
    override val id: DidId,

    /**
     * The new proof.
     */
    val proof: LdProof
) : DidCommand

/**
 * @d2 event
 * @parent [DidProofUpdateFunction]
 */
class DidProofUpdatedEvent(
    /**
     * The DID that has been updated.
     */
    override val id: DidId,

    /**
     * The current state of the DID.
     */
    override val type: DidState,

    /**
     * The updated DID Document with its new proof.
     */
    val document: DIDDocument
) : DidEvent
