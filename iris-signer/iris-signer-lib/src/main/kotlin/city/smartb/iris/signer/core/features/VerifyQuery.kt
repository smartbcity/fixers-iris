package city.smartb.iris.signer.core.features

import city.smartb.iris.crypto.dsl.verifier.Verifier
import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64
import city.smartb.iris.crypto.rsa.verifier.RS256Verifier
import city.smartb.iris.signer.domain.features.VerifyQueryFunction
import city.smartb.iris.signer.domain.features.VerifyQueryResult
import city.smartb.iris.vc.signer.VCVerifier
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
    @Bean
    open fun verifyQueryFunction(): VerifyQueryFunction = f2Function { query ->
        val proofType = query.vc.proof.type
        val keyUrl = query.vc.proof.verificationMethod

        val verifier = getVerifier(proofType, keyUrl)
        val verified = VCVerifier().verify(query.vc, verifier)

        VerifyQueryResult(verified)
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
