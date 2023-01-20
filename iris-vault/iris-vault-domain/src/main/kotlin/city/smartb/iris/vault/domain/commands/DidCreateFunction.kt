package city.smartb.iris.vault.domain.commands

import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias DidLibCreateCommand = city.smartb.iris.did.domain.commands.DidCreateCommand

typealias DidCreateFunction = F2Function<DidCreateCommand, DidCreatedEvent>

@Serializable
class DidCreateCommand

@Serializable
open class DidCreatedEvent(
    @Contextual
    val didDocument: DIDDocument
)
