package city.smartb.iris.crypto.rsa.verifier

import city.smartb.iris.crypto.dsl.verifier.Verifier
import com.nimbusds.jose.JWSAlgorithm
import java.security.GeneralSecurityException
import java.security.Signature
import java.security.interfaces.RSAPublicKey

class RS256Verifier(
    private val rsaPublicKey: RSAPublicKey
) : Verifier {

    override val algorithm: JWSAlgorithm
        get() = JWSAlgorithm.RS256

    @Throws(GeneralSecurityException::class)
    override fun verify(content: ByteArray, signature: ByteArray): Boolean {
        val jcaSignature = Signature.getInstance("SHA256withRSA")
        jcaSignature.initVerify(rsaPublicKey)
        jcaSignature.update(content)
        return jcaSignature.verify(signature)
    }
}
