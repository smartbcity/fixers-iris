package city.smartb.iris.signer.core.features.key

import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.vault.core.VaultOperations
import org.springframework.vault.support.VaultResponse

typealias VaultGetKeyQueryFunction = F2Function<VaultGetKeyQuery, VaultGetKeyQueryResult>

class VaultGetKeyQuery(
    val name: String
)

class VaultGetKeyQueryResult(
    val vaultRawResponse: VaultResponse?
)

@Configuration
open class VaultGetKeyCommandFunctionImpl(
    private val vaultOperations: VaultOperations
) {
    @Primary
    @Bean
    open fun vaultGetKeyCommandFunction(): VaultGetKeyQueryFunction = f2Function { query ->
        val response = vaultOperations.read("secret/data/${query.name}")

        VaultGetKeyQueryResult(response)
    }
}
