package city.smartb.iris.registrar.entity

import city.smartb.iris.did.DIDDocument
import city.smartb.iris.registrar.domain.DidId
import city.smartb.iris.registrar.domain.DidState
import java.util.UUID
import s2.dsl.automate.model.WithS2IdAndStatus

data class DidEntity(
	val document: DIDDocument,
	var state: Int,
) : WithS2IdAndStatus<DidId, DidState> {
	override fun s2Id() = document.id
	override fun s2State() = DidState(state)
}
