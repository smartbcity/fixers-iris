package city.smartb.iris.crypto.hc.vault.utils

import org.springframework.vault.support.VaultResponse

fun VaultResponse.getPrivateKey(): String {
    val data = this.data!!.get("data")!! as Map<String, Any>
    return data.get("privKey") as String
}

fun VaultResponse.getPublicKey(): String {
    val data = this.data!!.get("data")!! as Map<String, Any>
    return data.get("pubKey") as String
}

fun VaultResponse.getSignatureValue(): String {
    return this.data!!.get("signature")!! as String
}

fun VaultResponse.getTransitPublicKey(): String {
    val keys = this.data!!.get("keys") as Map<String, Any>
    val key = keys.get("1") as Map<String, String>
    return key["public_key"] ?: ""
}
