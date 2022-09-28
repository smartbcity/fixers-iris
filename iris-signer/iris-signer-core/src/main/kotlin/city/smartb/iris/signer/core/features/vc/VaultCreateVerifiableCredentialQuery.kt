package city.smartb.iris.signer.core.features.vc

import city.smartb.iris.signer.core.features.key.VaultGetKeyQuery
import city.smartb.iris.signer.core.features.key.VaultGetKeyQueryFunction
import city.smartb.iris.signer.core.signer.VerifiableCredentialSigner
import city.smartb.iris.signer.core.utils.getPrivateKey
import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invokeWith
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

typealias VaultCreateVerifiableCredentialQueryFunction = F2Function<VaultCreateVerifiableCredentialQuery, VaultCreateVerifiableCredentialQueryResult>

class VaultCreateVerifiableCredentialQuery(
    val keyName: String,
    val issuer: String,
    val subject: Any,
    val id: String
)

class VaultCreateVerifiableCredentialQueryResult(
    val vc: Any?
)

@Configuration
open class VaultCreateVerifiableCredentialQueryFunctionImpl(
    private val vaultGetKeyQueryFunction: VaultGetKeyQueryFunction
) {
    @Bean
    open fun vaultCreateVerifiableCredentialQueryFunction(): VaultCreateVerifiableCredentialQueryFunction = f2Function { query ->
        val vaultResponse = VaultGetKeyQuery(query.keyName).invokeWith(vaultGetKeyQueryFunction).vaultRawResponse
            ?: return@f2Function VaultCreateVerifiableCredentialQueryResult(null)

        val privKey = vaultResponse.getPrivateKey()

        val vc = VerifiableCredentialSigner.sign(query.id, query.issuer, query.subject, privKey, query.keyName)

        VaultCreateVerifiableCredentialQueryResult(vc.asJson())
    }
}
