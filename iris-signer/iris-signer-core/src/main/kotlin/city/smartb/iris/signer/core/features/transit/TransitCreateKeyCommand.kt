package city.smartb.iris.signer.core.features.transit

import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.vault.core.VaultOperations
import org.springframework.vault.support.VaultResponse


typealias TransitCreateKeyCommandFunction = F2Function<TransitCreateKeyCommand, TransitCreateKeyCommandResult>

class TransitCreateKeyCommand(
    val name: String,
    val type: String
)

class TransitCreateKeyCommandResult(
    val vaultRawResponse: VaultResponse?,
)

@Configuration
open class TransitCreateKeyCommandFunctionImpl(
    private val vaultOperations: VaultOperations,
) {
    @Bean
    open fun transitCreateKeyCommandFunction(): TransitCreateKeyCommandFunction = f2Function { query ->
        val response = vaultOperations.write("transit/keys/${query.name}", mapOf("type" to query.type))

        TransitCreateKeyCommandResult(response)
    }
}
