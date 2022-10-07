package city.smartb.iris.signer.api.service

import city.smartb.iris.crypto.hc.vault.utils.getPublicKey
import city.smartb.iris.crypto.hc.vault.utils.getTransitPublicKey
import city.smartb.iris.crypto.rsa.utils.FileUtils
import city.smartb.iris.signer.core.utils.RSA_KEYS_DIRECTORY
import org.springframework.vault.core.VaultOperations
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class KeyResolver(
    private val vaultOperations: VaultOperations,
) {

    @GetMapping("/resolvekey")
    fun getPublicKey(@RequestParam key: String, @RequestParam type: String): String? {
        return when(type) {
            "VaultTransit" -> getHcVaultTransitKey(key)
            "VaultKV" -> getHcVaultKVKey(key)
            "RsaKey" -> getRsaKey(key)
            else -> null
        }
    }

    private fun getHcVaultKVKey(keyName: String): String {
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
