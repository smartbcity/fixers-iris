package city.smartb.iris.signer.core.features.vc

import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64
import city.smartb.iris.crypto.rsa.verifier.Verifier
import city.smartb.iris.signer.core.features.key.VaultGetKeyQuery
import city.smartb.iris.signer.core.features.key.VaultGetKeyQueryFunction
import city.smartb.iris.signer.core.utils.getPublicKey
import city.smartb.iris.vc.VerifiableCredential
import city.smartb.iris.vc.signer.VCVerifier
import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invokeWith
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

typealias VaultVerifyVerifiableCredentialQueryFunction = F2Function<VaultVerifyVerifiableCredentialQuery, VaultVerifyVerifiableCredentialQueryResult>

class VaultVerifyVerifiableCredentialQuery(
    val keyName: String,
    val vc: VerifiableCredential
)

class VaultVerifyVerifiableCredentialQueryResult(
    val verified: Boolean?
)

@Configuration
open class VaultVerifyVerifiableCredentialQueryFunctionImpl(
    private val vaultGetKeyQueryFunction: VaultGetKeyQueryFunction
) {
    @Bean
    open fun vaultVerifyVerifiableCredentialQueryFunction(): VaultVerifyVerifiableCredentialQueryFunction = f2Function { query ->
        val vaultResponse = VaultGetKeyQuery(query.keyName).invokeWith(vaultGetKeyQueryFunction).vaultRawResponse
            ?: return@f2Function VaultVerifyVerifiableCredentialQueryResult(null)

        val pubKey = RSAKeyPairDecoderBase64.decodePublicKey(vaultResponse!!.getPublicKey())

        val verified = VCVerifier().verify(query.vc, Verifier.rs256Verifier(pubKey))

        VaultVerifyVerifiableCredentialQueryResult(verified)
    }
}
