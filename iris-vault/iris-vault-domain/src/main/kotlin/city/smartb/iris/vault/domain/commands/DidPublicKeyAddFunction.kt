package city.smartb.iris.vault.domain.commands

import city.smartb.iris.did.domain.DidId
import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual

/**
 * Create a new RSA Transit key and add it to the specified DID.
 * @d2 function
 * @parent [city.smartb.iris.vault.domain.D2IrisVaultPage]
 * @order 20
 */
typealias DidPublicKeyAddFunction = F2Function<DidPublicKeyAddCommand, DidPublicKeyAddedEvent>

/**
 * @d2 command
 * @parent [DidPublicKeyAddFunction]
 */
class DidPublicKeyAddCommand(
    /**
     * The DID to add the key to.
     */
    val did: DidId,

    /**
     * The name of the added key.
     */
    val keyName: String
)

/**
 * @d2 event
 * @parent [DidPublicKeyAddFunction]
 */
open class DidPublicKeyAddedEvent(
    /**
     * The DID Document updated with the public key of the new key.
     */
    @Contextual
    val didDocument: DIDDocument
)
