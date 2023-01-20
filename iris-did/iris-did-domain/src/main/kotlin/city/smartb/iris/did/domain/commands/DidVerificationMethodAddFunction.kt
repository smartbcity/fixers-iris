package city.smartb.iris.did.domain.commands

import city.smartb.iris.did.domain.DidCommand
import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias DidVerificationMethodAddFunction = F2Function<DidVerificationMethodAddCommand, DidVerificationMethodAddedEvent>

@Serializable
data class DidVerificationMethodAddCommand(
    override val id: DidId,
    val keyId: String,
    val type: String,
    val controller: String,
    val publicKey: String
) : DidCommand

@Serializable
open class DidVerificationMethodAddedEvent(
    override val id: DidId,
    override val type: DidState,
    @Contextual
    val document: DIDDocument
) : DidEvent
