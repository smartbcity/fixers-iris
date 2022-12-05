package city.smartb.iris.signer.core.features

import city.smartb.iris.crypto.dsl.verifier.Verifier
import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64
import city.smartb.iris.crypto.rsa.verifier.RS256Verifier
import city.smartb.iris.ldproof.VerifiableJsonLd
import city.smartb.iris.ldproof.crypto.RsaSignature2018LdProofVerifier
import city.smartb.iris.signer.domain.features.VerifyQueryFunction
import city.smartb.iris.signer.domain.features.VerifyQueryResult
import f2.dsl.fnc.f2Function
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.security.interfaces.RSAPublicKey
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class VerifyQueryFunctionImpl
{
    // Allow verifier to resolve towards vault keys - currently only supporting key coming from system file
    @Bean
    open fun verifyQueryFunction(): VerifyQueryFunction = f2Function { query ->
        val proofType = query.jsonLd.proof.type
        val keyUrl = query.jsonLd.proof.verificationMethod

        val verifier = getVerifier(proofType, keyUrl)

        VerifyQueryResult(verify(query.jsonLd, verifier))
    }

    private fun verify(jsonLdWithProof: VerifiableJsonLd, verifier: Verifier?): Boolean {
        val ldSigner = RsaSignature2018LdProofVerifier(verifier)
        return ldSigner.verify(jsonLdWithProof)
    }

    private fun getRsaPublicKey(url: String): RSAPublicKey {
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return RSAKeyPairDecoderBase64.decodePublicKey(response.body())
    }

    private fun getVerifier(type: String, keyUrl: String): Verifier? {
        return when(type) {
            "RsaSignature2018" -> RS256Verifier(getRsaPublicKey(keyUrl))
            else -> null
        }
    }
}
