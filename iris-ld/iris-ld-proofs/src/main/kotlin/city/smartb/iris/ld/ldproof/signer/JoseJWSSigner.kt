package city.smartb.iris.ld.ldproof.signer

import city.smartb.iris.crypto.dsl.signer.Signer
import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSSigner
import com.nimbusds.jose.crypto.impl.BaseJWSProvider
import com.nimbusds.jose.util.Base64URL
import java.security.GeneralSecurityException
import kotlinx.coroutines.runBlocking

class JoseJWSSigner(private val signer: Signer, algorithm: JWSAlgorithm?) : BaseJWSProvider(setOf(algorithm)),
    JWSSigner {
    @Throws(JOSEException::class)
    override fun sign(header: JWSHeader, signingInput: ByteArray): Base64URL = runBlocking {
        if (!supportedJWSAlgorithms().contains(header.algorithm)) throw JOSEException("Unexpected algorithm: " + header.algorithm)
        try {
            Base64URL.encode(signer.sign(signingInput))
        } catch (ex: GeneralSecurityException) {
            throw JOSEException(ex.message, ex)
        }
    }
}
