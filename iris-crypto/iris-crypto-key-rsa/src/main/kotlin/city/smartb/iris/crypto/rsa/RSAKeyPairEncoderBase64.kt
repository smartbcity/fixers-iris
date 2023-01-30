package city.smartb.iris.crypto.rsa

import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.Base64

object RSAKeyPairEncoderBase64 {

    fun encodePrivateKey(value: RSAPrivateKey): String {
        return encode(value.encoded)
    }

    fun encodePublicKey(value: RSAPublicKey): String {
        return encode(value.encoded)
    }

    private fun encode(value: ByteArray): String {
        return try {
            Base64.getUrlEncoder().encodeToString(value)
        } catch (e: Exception) {
            Base64.getEncoder().encodeToString(value)
        }
    }
}
