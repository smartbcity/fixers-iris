package city.smartb.iris.api.rest.sign

import city.smartb.iris.api.rest.model.jwt.asSHA256
import com.google.common.hash.Hashing
import com.nimbusds.jose.util.Base64URL
import org.assertj.core.api.Assertions
import org.bouncycastle.asn1.DERNull
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers
import org.bouncycastle.asn1.x509.AlgorithmIdentifier
import org.bouncycastle.asn1.x509.DigestInfo
import org.junit.jupiter.api.Test
import java.nio.charset.StandardCharsets
import java.security.Signature
import java.util.*
import java.security.PrivateKey


class SHA256KtTest {

    @Test
    fun `sha256 test`() {
        val digest =  Hashing.sha256().hashString("test", StandardCharsets.UTF_8).asBytes()
        val sha256 =  Base64.getUrlEncoder().encodeToString(digest)
        Assertions.assertThat(sha256).isEqualTo("n4bQgYhMfWWaL-qgxVrQFaO_TxsrC4Is0V1sFbDwCgg=")
    }


    @Test
    fun `should always return the same value`() {
        val test =  "test".toByteArray().asSHA256()
        val testBase64 = Base64.getUrlEncoder().encodeToString(test)
        Assertions.assertThat(testBase64).isEqualTo("n4bQgYhMfWWaL-qgxVrQFaO_TxsrC4Is0V1sFbDwCgg=")
    }

    @Test
    fun `should return the same signature when sign with NONEwithRSA`() {
        val signature =  "test".toByteArray().asSHA256()
        val privKey = KeyPairReader.loadPrivateKey("rsa/adam")
        val signed  = signNoneWithRSA(signature, privKey)
        Assertions.assertThat(signed).isEqualTo("Iixv9lizdEVr3qZsL81kIDMM4UNAwvU6cQcVnDvybOcbFO7XL8q5hjpzgGrF-z73BOhkhDI9BZJEQFlTm2ZiC7PkGroFUhcH9--wN64l-6KqIjO7hzBDlCBNEBkYQX3ZT1YFI9-QpWHY9_P1mZnbTmpWqvKkjBSkUEV5ISuYFdl_MKSKautYhq7T64yo606Zcu6qPP7LUUwaNSCfqTLG7_XCikWfy79aDL-4a11soeUwWkf0zX6-XrGbLEUZAy8rulv2rl7XftDGmJUoFUWNhujmwCm7HEGpHmVP81SJzqeG4OR6iQKYbgUfqm1B89XhX99JiVkrNBiDUintpWVvKg")
    }

    @Test
    fun `should return the same signature when sign with NONEwithRSA and SHA256withRSA`() {
        val signature =  "test".toByteArray().asSHA256()
        val privKey = KeyPairReader.loadPrivateKey("rsa/adam")

        val signed  = signNoneWithRSA(signature, privKey)
        val shaRSA = signSHA256withRSA("test".toByteArray(), privKey)

        Assertions.assertThat(signed).isEqualTo(shaRSA)
    }

    companion object {
        fun signNoneWithRSA(jsonLdObject: ByteArray, rsaPrivateKey: PrivateKey): String {
            val sha256Aid = AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE)
            val di = DigestInfo(sha256Aid, jsonLdObject)
            val jsonLdObject = di.toASN1Primitive().getEncoded();

            val privateSignature = Signature.getInstance("NONEwithRSA")
            privateSignature.initSign(rsaPrivateKey);
            privateSignature.update(jsonLdObject)
            val signed = privateSignature.sign()
            return Base64URL.encode(signed).toString()
        }

        fun signSHA256withRSA(jsonLdObject: ByteArray, rsaPrivateKey: PrivateKey): String {
            val privateSignature = Signature.getInstance("SHA256withRSA")
            privateSignature.initSign(rsaPrivateKey);
            privateSignature.update(jsonLdObject)

            return privateSignature.sign().let {
                Base64URL.encode(it).toString()
            }
        }
    }
}