package city.smartb.iris.signer.core.features

import city.smartb.iris.crypto.hc.vault.utils.getPublicKey
import city.smartb.iris.crypto.hc.vault.utils.getTransitPublicKey
import city.smartb.iris.crypto.rsa.utils.FileUtils
import city.smartb.iris.signer.core.utils.RSA_KEYS_DIRECTORY
import city.smartb.iris.signer.domain.features.GetKeyQuery
import city.smartb.iris.signer.domain.features.GetKeyQueryFunction
import city.smartb.iris.signer.domain.features.GetKeyQueryResult
import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.vault.core.VaultOperations

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

    private fun getHcVaultKVKey(keyName: String): String? {
        return vaultOperations.read("secret/data/$keyName")!!.getPublicKey()
    }

    private fun getHcVaultTransitKey(keyName: String): String {
        val pemString = vaultOperations.read("transit/keys/$keyName")?.getTransitPublicKey() ?: ""
        return pemPublicKeyToString(pemString)
    }

    private fun getRsaKey(keyName: String): String {
        val pemString = FileUtils.getFile("$RSA_KEYS_DIRECTORY/$keyName.pub").readText()
        return pemPublicKeyToString(pemString)
    }

    private fun pemPublicKeyToString(pemString: String): String {
        return pemString
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace(System.lineSeparator(), "")
            .replace("-----END PUBLIC KEY-----", "")
    }
}
