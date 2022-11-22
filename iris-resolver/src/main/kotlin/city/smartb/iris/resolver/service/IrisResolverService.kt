package city.smartb.iris.resolver.service

import city.smartb.iris.s2.domain.DidFinder
import city.smartb.iris.s2.domain.queries.DidGetQueryFunction
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class IrisResolverService(
    private val didGetQueryFunction: DidGetQueryFunction,
) : DidFinder {

    @Bean
    override fun getDid() = didGetQueryFunction
}
