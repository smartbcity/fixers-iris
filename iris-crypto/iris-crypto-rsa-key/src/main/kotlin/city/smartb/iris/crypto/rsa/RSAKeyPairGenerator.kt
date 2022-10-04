package city.smartb.iris.crypto.rsa

import city.smartb.iris.crypto.rsa.exception.CryptoException
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.NoSuchAlgorithmException

object RSAKeyPairGenerator {

    @Throws(CryptoException::class)
    fun generate2048Pair(): KeyPair {
        return try {
            val kpg = KeyPairGenerator.getInstance("RSA")
            kpg.initialize(2048)
            kpg.generateKeyPair()
        } catch (e: NoSuchAlgorithmException) {
            throw CryptoException("RSA key can't be generated", e)
        }
    }
}
