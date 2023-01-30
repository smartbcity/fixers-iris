package city.smartb.iris.did.s2.entity

import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import city.smartb.iris.ld.did.DIDDocument
import s2.dsl.automate.model.WithS2IdAndStatus

data class DidEntity(
	var document: DIDDocument,
	var state: Int,
) : WithS2IdAndStatus<DidId, DidState> {
	override fun s2Id() = document.id!!
	override fun s2State() = DidState(state)
}
