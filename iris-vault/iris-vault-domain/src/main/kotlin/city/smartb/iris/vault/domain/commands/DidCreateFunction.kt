package city.smartb.iris.vault.domain.commands

import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias DidLibCreateCommand = city.smartb.iris.did.domain.commands.DidCreateCommand

/**
 * Create a new DID document.
 * @d2 function
 * @parent [city.smartb.iris.vault.domain.D2IrisVaultPage]
 * @order 10
 */
typealias DidCreateFunction = F2Function<DidCreateCommand, DidCreatedEvent>

/**
 * @d2 command
 * @parent [DidCreateFunction]
 */
@Serializable
class DidCreateCommand

/**
 * @d2 event
 * @parent [DidCreateFunction]
 */
@Serializable
open class DidCreatedEvent(
    @Contextual
    /**
     * The newly created DID document.
     */
    val didDocument: DIDDocument
)
