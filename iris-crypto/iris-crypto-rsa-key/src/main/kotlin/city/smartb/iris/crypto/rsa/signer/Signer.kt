package city.smartb.iris.crypto.rsa.signer

import com.nimbusds.jose.JWSAlgorithm
import java.security.GeneralSecurityException
import java.security.interfaces.RSAPrivateKey

interface Signer {
    val algorithm: JWSAlgorithm?
    val term: String?

    @Throws(GeneralSecurityException::class)
    fun sign(content: ByteArray): ByteArray

    companion object {
        fun rs256Signer(rsaPrivateKey: RSAPrivateKey): RS256Signer {
            return RS256Signer(rsaPrivateKey)
        }
    }
}
