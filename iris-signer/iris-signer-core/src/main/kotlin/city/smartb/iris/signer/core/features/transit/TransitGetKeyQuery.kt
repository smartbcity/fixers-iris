package city.smartb.iris.signer.core.features.transit

import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.vault.core.VaultOperations
import org.springframework.vault.support.VaultResponse


typealias TransitGetKeyQueryFunction = F2Function<TransitGetKeyQuery, TransitGetKeyQueryResult>

class TransitGetKeyQuery(
    val name: String
)

class TransitGetKeyQueryResult(
    val vaultRawResponse: VaultResponse?,
)

@Configuration
open class TransitGetKeyQueryFunctionImpl(
    private val vaultOperations: VaultOperations,
) {
    @Bean
    open fun transitGetKeyQueryFunction(): TransitGetKeyQueryFunction = f2Function { query ->
        val response = vaultOperations.read("transit/keys/${query.name}")

        TransitGetKeyQueryResult(response)
    }
}
