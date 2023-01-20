package city.smartb.iris.vault.domain.commands

import city.smartb.iris.did.domain.DidId
import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual

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
