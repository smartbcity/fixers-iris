package city.smartb.iris.did.domain.commands

import city.smartb.iris.did.DIDDocument
import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidInitCommand
import city.smartb.iris.did.domain.DidState
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias DidCreateFunction = F2Function<DidCreateCommand, DidCreatedEvent>

@Serializable
open class DidCreateCommand: DidInitCommand

class DidCreatedEvent(
    override val id: String,
    override val type: DidState,
    val document: DIDDocument
) : DidEvent
