package city.smartb.iris.registrar.service

import city.smartb.iris.s2.domain.DidAggregate
import city.smartb.iris.s2.domain.commands.DidAddVerificationMethodCommandFunction
import city.smartb.iris.s2.domain.commands.DidCreateCommandFunction
import city.smartb.iris.s2.domain.commands.DidRevokeCommandFunction
import city.smartb.iris.s2.domain.commands.DidRevokeVerificationMethodCommandFunction
import city.smartb.iris.s2.domain.commands.DidUpdateCommandFunction
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class IrisRegistrarService(
	private val didCreateCommandFunction: DidCreateCommandFunction,
	private val didRevokeCommandFunction: DidRevokeCommandFunction,
	private val didAddVerificationMethodCommandFunction: DidAddVerificationMethodCommandFunction,
	private val didRevokeVerificationMethodCommandFunction: DidRevokeVerificationMethodCommandFunction,
	private val didUpdateCommandFunction: DidUpdateCommandFunction
) : DidAggregate {

	@Bean
	override fun createDid() = didCreateCommandFunction

	@Bean
	override fun addVerificationMethod() = didAddVerificationMethodCommandFunction

	@Bean
	override fun revokeVerificationMethod() = didRevokeVerificationMethodCommandFunction

	@Bean
	override fun revoke() = didRevokeCommandFunction

	@Bean
	override fun update() = didUpdateCommandFunction
}
