package city.smartb.iris.crypto.rsa.verifier

import com.nimbusds.jose.JWSAlgorithm
import java.security.GeneralSecurityException
import java.security.interfaces.RSAPublicKey

interface Verifier {
    val algorithm: JWSAlgorithm?

    @Throws(GeneralSecurityException::class)
    fun verify(content: ByteArray, signature: ByteArray): Boolean

    companion object {
        fun rs256Verifier(rsaPublicKey: RSAPublicKey): RS256Verifier {
            return RS256Verifier(rsaPublicKey)
        }
    }
}
