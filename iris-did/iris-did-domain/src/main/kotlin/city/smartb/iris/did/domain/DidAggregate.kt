package city.smartb.iris.did.domain

import city.smartb.iris.did.domain.commands.DidCreateFunction
import city.smartb.iris.did.domain.commands.DidRevokeFunction
import city.smartb.iris.did.domain.commands.DidRevokeVerificationMethodCommandFunction
import city.smartb.iris.did.domain.commands.DidUpdateFunction
import city.smartb.iris.did.domain.commands.DidVerificationMethodAddFunction

interface DidAggregate {
	fun createDid(): DidCreateFunction
	fun addVerificationMethod(): DidVerificationMethodAddFunction
	fun revokeVerificationMethod(): DidRevokeVerificationMethodCommandFunction
	fun revoke(): DidRevokeFunction
	fun update(): DidUpdateFunction
}
