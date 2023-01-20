package city.smartb.iris.did.domain.commands

import city.smartb.iris.did.domain.DidCommand
import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import city.smartb.iris.ld.did.DIDDocument
import city.smartb.iris.ld.ldproof.LdProof
import f2.dsl.fnc.F2Function

typealias DidProofUpdateFunction = F2Function<DidProofUpdateCommand, DidProofUpdatedEvent>

class DidProofUpdateCommand(
    override val id: DidId,
    val proof: LdProof
) : DidCommand

class DidProofUpdatedEvent(
    override val id: DidId,
    override val type: DidState,
    val document: DIDDocument
) : DidEvent
