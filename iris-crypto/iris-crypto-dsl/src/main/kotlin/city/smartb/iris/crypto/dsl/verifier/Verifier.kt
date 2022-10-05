package city.smartb.iris.crypto.dsl.verifier

import com.nimbusds.jose.JWSAlgorithm
import java.security.GeneralSecurityException
import java.security.interfaces.RSAPublicKey

interface Verifier {
    val algorithm: JWSAlgorithm?

    @Throws(GeneralSecurityException::class)
    fun verify(content: ByteArray, signature: ByteArray): Boolean
}
