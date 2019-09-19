package city.smartb.iris.api.rest.sign

import com.google.common.io.Resources
import org.bouncycastle.asn1.pkcs.RSAPrivateKey
import org.bouncycastle.crypto.CryptoException
import org.bouncycastle.util.io.pem.PemObject
import org.bouncycastle.util.io.pem.PemReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.net.MalformedURLException
import java.net.URL
import java.security.*
import java.security.interfaces.RSAPublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.RSAPrivateCrtKeySpec
import java.security.spec.X509EncodedKeySpec


object KeyPairReader {

    private const val FILE = "file:"

    @Throws(CryptoException::class)
    fun loadKeyPair(filename: String): KeyPair {
        val priv = loadPrivateKey(filename)
        val pub = loadPublicKey(filename)
        return KeyPair(pub, priv)
    }

    @Throws(CryptoException::class)
    fun loadPrivateKey(filename: String): PrivateKey {
        try {
            val pem = getPemObject(filename)
            val key = RSAPrivateKey.getInstance(pem.content)
            val kf = KeyFactory.getInstance("RSA")
            val privSpec = RSAPrivateCrtKeySpec(key.modulus, key.publicExponent, key.privateExponent, key.prime1, key.prime2, key.exponent1, key.exponent2, key.coefficient)
            return kf.generatePrivate(privSpec)
        } catch (e: InvalidKeySpecException) {
            throw CryptoException("Private key can't be loaded", e)
        } catch (e: NoSuchAlgorithmException) {
            throw CryptoException("Private key can't be loaded", e)
        } catch (e: IOException) {
            throw CryptoException("Private key can't be loaded", e)
        }

    }

    @Throws(CryptoException::class)
    fun loadPublicKey(filename: String): PublicKey {
        var filenameCopy = filename
        try {
            if (!filenameCopy.endsWith(".pub")) {
                filenameCopy = "$filenameCopy.pub"
            }
            val pem = getPemObject(filenameCopy)
            val pubKeyBytes = pem.content

            val pubSpec = X509EncodedKeySpec(pubKeyBytes)
            val kf = KeyFactory.getInstance("RSA")
            return kf.generatePublic(pubSpec) as RSAPublicKey
        } catch (e: InvalidKeySpecException) {
            throw CryptoException("Public key can't be loaded", e)
        } catch (e: NoSuchAlgorithmException) {
            throw CryptoException("Public key can't be loaded", e)
        } catch (e: IOException) {
            throw CryptoException("Public key can't be loaded", e)
        }

    }


    @Throws(CryptoException::class)
    fun fromByteArray(pub: ByteArray): PublicKey {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(pub))
        } catch (e: InvalidKeySpecException) {
            throw CryptoException("Public key can't be loaded", e)
        } catch (e: NoSuchAlgorithmException) {
            throw CryptoException("Public key can't be loaded", e)
        }

    }

    @Throws(CryptoException::class)
    fun generateRSAKey(): KeyPair {
        try {
            val kpg = KeyPairGenerator.getInstance("RSA")
            kpg.initialize(2048)
            return kpg.generateKeyPair()
        } catch (e: NoSuchAlgorithmException) {
            throw CryptoException("RSA key can't be generated", e)
        }

    }

    @Throws(IOException::class)
    private fun getPemObject(filename: String): PemObject {
        val reader = getReader(filename)
        val rpem = PemReader(reader)
        return rpem.readPemObject()
    }

    @Throws(IOException::class)
    fun getReader(filename: String): Reader {
        val url = getUrl(filename)
        return InputStreamReader(url.openStream())
    }

    @Throws(MalformedURLException::class)
    fun getUrl(filename: String): URL {
        return if (filename.startsWith(FILE)) {
            URL(filename)
        } else Resources.getResource(filename)
    }

}
