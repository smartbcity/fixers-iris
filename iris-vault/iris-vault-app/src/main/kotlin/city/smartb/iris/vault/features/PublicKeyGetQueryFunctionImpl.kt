package city.smartb.iris.vault.features

import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64
import city.smartb.iris.signer.core.IrisSignerService
import city.smartb.iris.signer.domain.features.GetKeyQuery
import city.smartb.iris.vault.domain.queries.PublicKeyGetEvent
import city.smartb.iris.vault.domain.queries.PublicKeyGetQuery
import city.smartb.iris.vault.domain.queries.PublicKeyGetQueryFunction
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class PublicKeyGetQueryFunctionImpl(
    private val irisSignerService: IrisSignerService,
) {

    @Bean
    open fun publicKeyGetQueryFunction(): PublicKeyGetQueryFunction = f2Function { cmd ->
        getPublicKey(cmd)
    }

    private fun getPublicKey(cmd: PublicKeyGetQuery): PublicKeyGetEvent = runBlocking {
        val publicKey = irisSignerService.getKey()
            .invoke(GetKeyQuery(keyName = cmd.name, type = "HcVaultKV")).publicKey
            ?: throw Exception("Key [${cmd.name}] not found")

        PublicKeyGetEvent(
            publicKey = formatPublicKey(publicKey, cmd.format)
        )
    }

    private fun formatPublicKey(publicKey: String, format: String): Map<String,Any> {
        return when (format) {
            "jwk" -> publicKeyToJwk(publicKey)
            "base64" -> publicKeyToBase64(publicKey)
            else -> { throw Exception("Unknown output format") }
        }
    }

    private fun publicKeyToJwk(publicKey: String): Map<String, Any> {
        return RSAKey.Builder(RSAKeyPairDecoderBase64.decodePublicKey(publicKey))
            .keyUse(KeyUse.SIGNATURE)
            .build()
            .toPublicJWK()
            .toJSONObject()
    }

    private fun publicKeyToBase64(publicKey: String): Map<String, Any> {
        return mapOf("key" to publicKey)
    }
}
