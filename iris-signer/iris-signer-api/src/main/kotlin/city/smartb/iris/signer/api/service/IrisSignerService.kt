package city.smartb.iris.signer.api.service

import city.smartb.iris.signer.core.features.CreateKeyCommandFunction
import city.smartb.iris.signer.core.features.GetKeyQueryFunction
import city.smartb.iris.signer.core.features.SignCommandFunction
import city.smartb.iris.signer.core.features.VerifyCommandFunction
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class IrisSignerService(
    private val signCommandFunction: SignCommandFunction,
    private val verifyCommandFunction: VerifyCommandFunction,
    private val createKeyCommandFunction: CreateKeyCommandFunction,
    private val getKeyQueryFunction: GetKeyQueryFunction
) {
    @Bean
    fun sign() = signCommandFunction

    @Bean
    fun verify() = verifyCommandFunction

    @Bean
    fun createKey() = createKeyCommandFunction

    @Bean
    fun getKey() = getKeyQueryFunction
}
