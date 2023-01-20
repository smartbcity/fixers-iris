package city.smartb.iris.ld.ldproof.verifier

import city.smartb.iris.crypto.dsl.verifier.Verifier
import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSVerifier
import com.nimbusds.jose.crypto.impl.BaseJWSProvider
import com.nimbusds.jose.util.Base64URL
import java.security.GeneralSecurityException

class JoseJWSVerifier(private val verifier: Verifier, algorithm: JWSAlgorithm) : BaseJWSProvider(setOf(algorithm)),
    JWSVerifier {
    @Throws(JOSEException::class)
    override fun verify(header: JWSHeader, signingInput: ByteArray, signature: Base64URL): Boolean {
        if (!supportedJWSAlgorithms().contains(header.algorithm)) throw JOSEException("Unexpected algorithm: " + header.algorithm)
        return try {
            verifier.verify(signingInput, signature.decode())
        } catch (ex: GeneralSecurityException) {
            throw JOSEException(ex.message, ex)
        }
    }
}
