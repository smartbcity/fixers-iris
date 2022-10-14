package city.smartb.iris.signer.core

import city.smartb.iris.signer.domain.IrisSignerAggregateService
import city.smartb.iris.signer.domain.IrisSignerFinderService
import city.smartb.iris.signer.domain.features.CreateKeyCommandFunction
import city.smartb.iris.signer.domain.features.GetKeyQueryFunction
import city.smartb.iris.signer.domain.features.SignQueryFunction
import city.smartb.iris.signer.domain.features.VerifyQueryFunction
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class IrisSignerService(
    private val signCommandFunction: SignQueryFunction,
    private val verifyCommandFunction: VerifyQueryFunction,
    private val createKeyCommandFunction: CreateKeyCommandFunction,
    private val getKeyQueryFunction: GetKeyQueryFunction
): IrisSignerAggregateService, IrisSignerFinderService {
    @Bean
    override fun sign() = signCommandFunction

    @Bean
    override fun verify() = verifyCommandFunction

    @Bean
    override fun createKey() = createKeyCommandFunction

    @Bean
    override fun getKey() = getKeyQueryFunction
}
