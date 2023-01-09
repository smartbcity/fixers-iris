package city.smartb.iris.vault.domain.commands

import city.smartb.iris.did.DIDDocument
import city.smartb.iris.did.domain.DidId
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias DidPublicKeyAddFunction = F2Function<DidPublicKeyAddCommand, DidPublicKeyAddedEvent>

class DidPublicKeyAddCommand(
    val did: DidId,
    val publicKey: String,
    val type: String
)

open class DidPublicKeyAddedEvent(
    @Contextual
    val didDocument: DIDDocument
)
