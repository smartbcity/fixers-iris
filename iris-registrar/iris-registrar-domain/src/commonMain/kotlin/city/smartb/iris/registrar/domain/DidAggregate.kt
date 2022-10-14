package city.smartb.iris.registrar.domain

import city.smartb.iris.registrar.domain.features.DidAddPublicKeyCommandFunction
import city.smartb.iris.registrar.domain.features.DidCreateCommandFunction
import city.smartb.iris.registrar.domain.features.DidRevokeCommandFunction
import city.smartb.iris.registrar.domain.features.DidRevokePublicKeyCommandFunction

interface DidAggregate {
	fun createDid(): DidCreateCommandFunction
	fun addPublicKey(): DidAddPublicKeyCommandFunction
	fun revokePublicKey(): DidRevokeCommandFunction
	fun revoke(): DidRevokePublicKeyCommandFunction
}
