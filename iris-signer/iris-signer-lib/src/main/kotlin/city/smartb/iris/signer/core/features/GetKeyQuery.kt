package city.smartb.iris.signer.core.features

import city.smartb.iris.signer.domain.features.GetKeyQueryFunction
import city.smartb.iris.signer.domain.features.GetKeyQueryResult
import city.smartb.iris.vault.client.VaultClient
import city.smartb.iris.vault.domain.queries.TransitPublicKeyGetQuery
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class GetKeyQueryFunctionImpl(
    private val vaultClient: VaultClient
) {
    @Bean
    open fun getKeyQueryFunction(): GetKeyQueryFunction = f2Function { query ->
        GetKeyQueryResult(
            when(query.method) {
//                "RsaKey" -> getRsaKey(query.keyName)
//                "HcVaultKV" -> getHcVaultKVKey(query.keyName)
                "transit" -> getHcVaultTransitKey(query.keyName)
                else -> ""
            }
        )
    }

//    private fun getHcVaultKVKey(keyName: String): String? {
//        return vaultClient.secretGet(SecretGetQuery(keyName))
//        return vaultOperations.read("secret/data/$keyName")!!.getPublicKey()
//    }

    private suspend fun getHcVaultTransitKey(keyName: String): String {
        return vaultClient.transitPublicKeyGet(TransitPublicKeyGetQuery(keyName)).publicKey
    }

//    private fun getRsaKey(keyName: String): String {
//        val pemString = FileUtils.getFile("$RSA_KEYS_DIRECTORY/$keyName.pub").readText()
//        return pemPublicKeyToString(pemString)
//    }

    private fun pemPublicKeyToString(pemString: String): String {
        return pemString
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace(System.lineSeparator(), "")
            .replace("-----END PUBLIC KEY-----", "")
    }
}
