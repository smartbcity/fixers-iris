package city.smartb.iris.crypto.dsl.signer

import com.nimbusds.jose.JWSAlgorithm
import java.security.GeneralSecurityException

interface Signer {
    val algorithm: JWSAlgorithm?
    val term: String

    @Throws(GeneralSecurityException::class)
    suspend fun sign(content: ByteArray): ByteArray
}
