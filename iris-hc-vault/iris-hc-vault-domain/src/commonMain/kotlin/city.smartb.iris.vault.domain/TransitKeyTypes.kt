package city.smartb.iris.vault.domain

object TransitKeyTypes {
    const val AES128_GCM96 = "aes128-gcm96"
    const val AES256_GCM96 = "aes256-gcm96"
    const val CHACHA20_POLY1305 = "chacha20-poly1305"
    const val ED25519 = "ed25519"
    const val ECDSA_P256 = "ecdsa-p256"
    const val ECDSA_P384 = "ecdsa-p384"
    const val ECDSA_P521 = "ecdsa-p521"
    const val RSA_2048 = "rsa-2048"
    const val RSA_3072 = "rsa-3072"
    const val RSA_4096 = "rsa-4096"

    fun toList(): List<String> {
        return listOf(
            AES128_GCM96,
            AES256_GCM96,
            CHACHA20_POLY1305,
            ED25519,
            ECDSA_P256,
            ECDSA_P384,
            ECDSA_P521,
            RSA_2048,
            RSA_3072,
            RSA_4096
        )
    }
}
