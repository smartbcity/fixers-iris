package city.smartb.iris.crypto.rsa

import city.smartb.iris.crypto.rsa.exception.InvalidRsaKeyException
import city.smartb.iris.crypto.rsa.utils.FileUtils
import java.io.IOException
import java.io.Reader
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.NoSuchAlgorithmException
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.RSAPrivateCrtKeySpec
import java.security.spec.X509EncodedKeySpec
import org.bouncycastle.asn1.pkcs.RSAPrivateKey
import org.bouncycastle.util.io.pem.PemObject
import org.bouncycastle.util.io.pem.PemReader

object RSAKeyPairReader {

    @Throws(InvalidRsaKeyException::class)
    fun loadKeyPair(filename: String): KeyPair {
        val priv = loadPrivateKey(filename)
        val pub = loadPublicKey(filename)
        return KeyPair(pub, priv)
    }

    @Throws(InvalidRsaKeyException::class)
    fun loadPrivateKey(filename: String): PrivateKey {
        return try {
            val pem = getPemObject(filename)
            val key = RSAPrivateKey.getInstance(pem.content)
            val kf = KeyFactory.getInstance("RSA")
            val privSpec = RSAPrivateCrtKeySpec(
                key.modulus,
                key.publicExponent,
                key.privateExponent,
                key.prime1,
                key.prime2,
                key.exponent1,
                key.exponent2,
                key.coefficient
            )
            kf.generatePrivate(privSpec)
        } catch (e: InvalidKeySpecException) {
            throw InvalidRsaKeyException("Private key can't be loaded", e)
        } catch (e: NoSuchAlgorithmException) {
            throw InvalidRsaKeyException("Private key can't be loaded", e)
        } catch (e: IOException) {
            throw InvalidRsaKeyException("Private key can't be loaded", e)
        }
    }

    @Throws(InvalidRsaKeyException::class)
    fun loadPublicKey(filename: String): PublicKey {
        var filename = filename
        return try {
            if (!filename.endsWith(".pub")) {
                filename = "$filename.pub"
            }
            val pem = getPemObject(filename)
            val pubKeyBytes = pem.content
            val pubSpec = X509EncodedKeySpec(pubKeyBytes)
            val kf = KeyFactory.getInstance("RSA")
            kf.generatePublic(pubSpec)
        } catch (e: InvalidKeySpecException ) {
            throw InvalidRsaKeyException("Public key can't be loaded", e)
        } catch (e: NoSuchAlgorithmException) {
            throw InvalidRsaKeyException("Public key can't be loaded", e)
        } catch (e: IOException) {
            throw InvalidRsaKeyException("Public key can't be loaded", e)
        }
    }

    @Throws(InvalidRsaKeyException::class)
    fun fromByteArray(pub: ByteArray?): PublicKey {
        return try {
            KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(pub))
        } catch (e: InvalidKeySpecException) {
            throw InvalidRsaKeyException("Public key can't be loaded", e)
        } catch (e: NoSuchAlgorithmException) {
            throw InvalidRsaKeyException("Public key can't be loaded", e)
        }
    }

    @Throws(InvalidRsaKeyException::class)
    fun generateRSAKey(): KeyPair {
        return try {
            val kpg = KeyPairGenerator.getInstance("RSA")
            kpg.initialize(2048)
            kpg.generateKeyPair()
        } catch (e: NoSuchAlgorithmException) {
            throw InvalidRsaKeyException("RSA key can't be generated", e)
        }
    }

    @Throws(IOException::class)
    private fun getPemObject(filename: String): PemObject {
        val reader: Reader = FileUtils.getReader(filename)
        val rpem = PemReader(reader)
        return rpem.readPemObject()
    }
}
