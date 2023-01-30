package city.smartb.iris.keypair.domain

import f2.dsl.fnc.F2Function

/**
 * Add a new transit key.
 * @d2 function
 * @parent [city.smartb.iris.keypair.domain.D2KeypairPage]
 * @order 10
 */
typealias KeypairCreateFunction = F2Function<KeypairCreateCommand, KeypairCreatedEvent>

/**
 * @d2 command
 * @parent [KeypairCreateFunction]
 */
open class KeypairCreateCommand(
    /**
     * The name used to identify the transit key in the vault.
     */
    val keyName: String
)

/**
 * @d2 event
 * @parent [KeypairCreateFunction]
 */
class KeypairCreatedEvent(
    /**
     * The name of the created transit key.
     */
    val id: String,

    /**
     * The value of the public key of the created transit key.
     */
    val publicKey: String
)
