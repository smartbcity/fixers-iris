package city.smartb.iris.crypto.rsa.signer

import city.smartb.iris.crypto.dsl.signer.Signer
import com.nimbusds.jose.JWSAlgorithm
import java.security.GeneralSecurityException
import java.security.Signature
import java.security.interfaces.RSAPrivateKey

class RS256Signer(
    private val rsaPrivateKey: RSAPrivateKey
) : Signer {

    override val algorithm: JWSAlgorithm
        get() = JWSAlgorithm.RS256
    override val term: String
        get() = "RsaSignature2018"

    @Throws(GeneralSecurityException::class)
    override fun sign(content: ByteArray): ByteArray {
        val jcaSignature = Signature.getInstance("SHA256withRSA")
        jcaSignature.initSign(rsaPrivateKey)
        jcaSignature.update(content)
        return jcaSignature.sign()
    }
}
