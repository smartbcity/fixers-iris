package city.smartb.iris.signer.core.utils

import org.springframework.vault.support.VaultResponse

fun VaultResponse.getPrivateKey(): String {
    val data = this.data!!.get("data")!! as Map<String, Any>
    return data.get("privKey") as String
}

fun VaultResponse.getPublicKey(): String {
    val data = this.data!!.get("data")!! as Map<String, Any>
    return data.get("pubKey") as String
}
