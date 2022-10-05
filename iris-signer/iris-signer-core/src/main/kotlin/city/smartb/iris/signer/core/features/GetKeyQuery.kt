package city.smartb.iris.signer.core.features

import city.smartb.iris.crypto.rsa.utils.FileUtils
import city.smartb.iris.signer.core.utils.RSA_KEYS_DIRECTORY
import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.vault.core.VaultOperations

typealias GetKeyQueryFunction = F2Function<GetKeyQuery, GetKeyQueryResult>

class GetKeyQuery(
    val keyName: String,
    val type: String
)

class GetKeyQueryResult(
    val response: Any?
)

@Configuration
open class GetKeyQueryFunctionImpl(
    private val vaultOperations: VaultOperations
) {

    @Bean
    open fun getKeyQueryFunction(): GetKeyQueryFunction = f2Function { query ->
        GetKeyQueryResult(
            when(query.type) {
                "RsaKey" -> getRsaKey(query.keyName)
                "HcVaultKV" -> getHcVaultKVKey(query.keyName)
                "HcVaultTransit" -> getHcVaultTransitKey(query.keyName)
                else -> null
            }
        )
    }

    private fun getHcVaultKVKey(keyName: String): Any? {
        return vaultOperations.read("secret/data/$keyName")
    }

    private fun getHcVaultTransitKey(keyName: String): Any? {
        return vaultOperations.read("transit/keys/$keyName")
    }

    private fun getRsaKey(keyName: String): String? {
        return FileUtils.getFile("$RSA_KEYS_DIRECTORY/$keyName.pub").readText()
    }
}
