package city.smartb.iris.did.domain.commands

import city.smartb.iris.did.DIDDocument
import city.smartb.iris.did.domain.DidCommand
import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import city.smartb.iris.ldproof.LdProof
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual

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
