package city.smartb.iris.did.domain.commands

import city.smartb.iris.did.domain.DidCommand
import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.F2Function

typealias DidUpdateFunction = F2Function<DidUpdateCommand, DidUpdatedEvent>

class DidUpdateCommand(
    override val id: DidId,
    val document: DIDDocument
) : DidCommand

class DidUpdatedEvent(
    override val id: DidId,
    override val type: DidState,
    val document: DIDDocument
) : DidEvent
