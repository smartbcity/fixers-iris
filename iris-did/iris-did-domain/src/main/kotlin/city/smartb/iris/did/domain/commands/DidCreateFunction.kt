package city.smartb.iris.did.domain.commands


import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidInitCommand
import city.smartb.iris.did.domain.DidState
import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Serializable

/**
 * Create a new empty DID document.
 * @d2 function
 * @parent [city.smartb.iris.did.domain.D2DidPage]
 * @order 10
 */
typealias DidCreateFunction = F2Function<DidCreateCommand, DidCreatedEvent>

/**
 * @d2 command
 * @parent [DidCreateFunction]
 */
@Serializable
open class DidCreateCommand: DidInitCommand

/**
 * @d2 event
 * @parent [DidCreateFunction]
 */
class DidCreatedEvent(
    /**
     * The DID of the newly created DID Document.
     */
    override val id: String,

    /**
     * The current state of the DID Document.
     */
    override val type: DidState,

    /**
     * The newly created DID Document.
     */
    val document: DIDDocument
) : DidEvent
