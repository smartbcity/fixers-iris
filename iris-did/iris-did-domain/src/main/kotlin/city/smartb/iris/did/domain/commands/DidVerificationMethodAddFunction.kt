package city.smartb.iris.did.domain.commands

import city.smartb.iris.did.domain.DidCommand
import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Add a new verification method to the DID Document.
 * @d2 function
 * @parent [city.smartb.iris.did.domain.D2DidPage]
 * @order 60
 */
typealias DidVerificationMethodAddFunction = F2Function<DidVerificationMethodAddCommand, DidVerificationMethodAddedEvent>

/**
 * @d2 command
 * @parent [DidVerificationMethodAddFunction]
 */
@Serializable
data class DidVerificationMethodAddCommand(
    /**
     * The DID to add the verification method to.
     */
    override val id: DidId,

    /**
     * The name of the added verification method.
     */
    val keyId: String,

    /**
     * The type of the added verification method.
     */
    val type: String,

    /**
     * The controller of the added verification method.
     */
    val controller: String,

    /**
     * The value of the public key of the added verification method.
     */
    val publicKey: String
) : DidCommand

/**
 * @d2 event
 * @parent [DidVerificationMethodAddFunction]
 */
@Serializable
open class DidVerificationMethodAddedEvent(
    override val id: DidId,
    override val type: DidState,
    @Contextual
    val document: DIDDocument
) : DidEvent
