package city.smartb.iris.vault.service

import city.smartb.iris.vault.domain.commands.PrivateKeyGenerateCommandFunction
import city.smartb.iris.vault.domain.commands.SignCommandFunction
import city.smartb.iris.vault.domain.queries.PublicKeyGetQueryFunction
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class IrisVaultService(
	private val privateKeyGenerateCommandFunction: PrivateKeyGenerateCommandFunction,
	private val getPublicKeyGetQueryFunction: PublicKeyGetQueryFunction,
	private val signCommandFunction: SignCommandFunction
) {
	@Bean
	fun generatePrivateKey() = privateKeyGenerateCommandFunction

	@Bean
	fun getPublicKey() = getPublicKeyGetQueryFunction

	@Bean
	fun irisSign() = signCommandFunction
}
