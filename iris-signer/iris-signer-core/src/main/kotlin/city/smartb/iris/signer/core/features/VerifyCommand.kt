package city.smartb.iris.signer.core.features

import city.smartb.iris.crypto.dsl.verifier.Verifier
import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64
import city.smartb.iris.crypto.rsa.verifier.RS256Verifier
import city.smartb.iris.vc.VerifiableCredential
import city.smartb.iris.vc.signer.VCVerifier
import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.security.interfaces.RSAPublicKey
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

typealias VerifyCommandFunction = F2Function<VerifyCommand, VerifyCommandResult>

class VerifyCommand(
    val vc: VerifiableCredential
)

class VerifyCommandResult(
    val verified: Boolean?
)

@Configuration
open class VerifyCommandFunctionImpl
{

    @Bean
    open fun verifyCommandFunction(): VerifyCommandFunction = f2Function { query ->
        val proofType = query.vc.proof.type
        val keyUrl = query.vc.proof.verificationMethod

        val verifier = getVerifier(proofType, keyUrl)
        val verified = VCVerifier().verify(query.vc, verifier)

        VerifyCommandResult(verified)
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
