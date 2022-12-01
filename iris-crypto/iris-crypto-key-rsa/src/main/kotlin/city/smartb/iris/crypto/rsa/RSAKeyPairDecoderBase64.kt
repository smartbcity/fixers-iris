package city.smartb.iris.crypto.rsa

import java.security.InvalidKeyException
import java.security.KeyFactory
import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import org.bouncycastle.util.io.pem.PemObject

object RSAKeyPairDecoderBase64 {

    @Throws(InvalidKeyException::class)
    fun decode(publicKey: String?, privateKey: String?): KeyPair {
        val pub: PublicKey = decodePublicKey(publicKey)
        val priv: PrivateKey = decodePrivateKey(privateKey)
        return KeyPair(pub, priv)
    }

    @Throws(InvalidKeyException::class)
    fun decodePrivateKey(value: String?): RSAPrivateKey {
        return try {
            val kf = KeyFactory.getInstance("RSA")
            val keySpecPKCS8 = PKCS8EncodedKeySpec(decode(value))
            kf.generatePrivate(keySpecPKCS8) as RSAPrivateKey
        } catch (e: Exception) {
            throw InvalidKeyException("Invalid private public key", e)
        }
    }

    @Throws(InvalidKeyException::class)
    fun decodePublicKey(value: String?): RSAPublicKey {
        return try {
            val bytes = decode(value)
            val pem = PemObject("PUBLIC KEY", bytes)
            val spec = X509EncodedKeySpec(pem.content)
            KeyFactory.getInstance("RSA").generatePublic(spec) as RSAPublicKey
        } catch (e: Exception) {
            throw InvalidKeyException("Invalid jwt public key", e)
        }
    }

    private fun decode(value: String?): ByteArray {
        return try {
            Base64.getUrlDecoder().decode(value)
        } catch (e: Exception) {
            Base64.getDecoder().decode(value)
        }
    }
}
