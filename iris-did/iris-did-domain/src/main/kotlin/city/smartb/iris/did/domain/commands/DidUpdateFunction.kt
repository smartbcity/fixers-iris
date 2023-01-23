package city.smartb.iris.did.domain.commands

import city.smartb.iris.did.domain.DidCommand
import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.F2Function

/**
 * Update a DID Document with the provided DID Document. It fully overrides the current document.
 * NOT TESTED YET
 * @d2 function
 * @parent [city.smartb.iris.did.domain.D2DidPage]
 * @order 40
 */
typealias DidUpdateFunction = F2Function<DidUpdateCommand, DidUpdatedEvent>

/**
 * @d2 command
 * @parent [DidUpdateFunction]
 */
class DidUpdateCommand(
    /**
     * The DID to update.
     */
    override val id: DidId,

    /**
     * The new DID Document to replace with.
     */
    val document: DIDDocument
) : DidCommand

class DidUpdatedEvent(
    /**
     * The DID that has been updated.
     */
    override val id: DidId,

    /**
     * The current state of the DID.
     */
    override val type: DidState,

    /**
     * The update DID Document.
     */
    val document: DIDDocument
) : DidEvent
