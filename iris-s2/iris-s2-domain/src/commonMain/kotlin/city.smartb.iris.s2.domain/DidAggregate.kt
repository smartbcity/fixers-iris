package city.smartb.iris.s2.domain

import city.smartb.iris.s2.domain.commands.DidAddVerificationMethodCommandFunction
import city.smartb.iris.s2.domain.commands.DidCreateCommandFunction
import city.smartb.iris.s2.domain.commands.DidRevokeCommandFunction
import city.smartb.iris.s2.domain.commands.DidRevokeVerificationMethodCommandFunction
import city.smartb.iris.s2.domain.commands.DidUpdateCommandFunction

interface DidAggregate {
	fun createDid(): DidCreateCommandFunction
	fun addVerificationMethod(): DidAddVerificationMethodCommandFunction
	fun revokeVerificationMethod(): DidRevokeVerificationMethodCommandFunction
	fun revoke(): DidRevokeCommandFunction
	fun update(): DidUpdateCommandFunction
}
