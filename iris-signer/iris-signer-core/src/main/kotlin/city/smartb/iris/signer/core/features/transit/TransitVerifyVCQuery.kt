package city.smartb.iris.signer.core.features.transit

import city.smartb.iris.ldproof.LdJsonObject
import city.smartb.iris.ldproof.LdJsonObjectBuilder
import city.smartb.iris.ldproof.LdProofBuilder
import city.smartb.iris.ldproof.util.SHAUtil
import city.smartb.iris.vc.VerifiableCredential
import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import java.util.Base64
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.vault.core.VaultOperations
import org.springframework.vault.support.VaultResponse


typealias TransitVerifyVCQueryFunction = F2Function<TransitVerifyVCQuery, TransitVerifyVCResult>

class TransitVerifyVCQuery(
    val keyName: String,
    val vc: VerifiableCredential
)

class TransitVerifyVCResult(
    val verified: VaultResponse?,
)

@Configuration
open class TransitVerifyVCQueryFunctionImpl(
    private val vaultOperations: VaultOperations
) {
    @Bean
    open fun transitVerifyVCQueryFunction(): TransitVerifyVCQueryFunction = f2Function { query ->
        val ldObject = LdJsonObject(query.vc.asJson())
        val proof = ldObject.proof
        val builder = LdProofBuilder.fromLdProof(proof)

        val canonicalizedProof = builder.canonicalize()
        val canonicalizedDocument = LdJsonObjectBuilder.builder(query.vc.asJson()).buildCanonicalizedDocument()

        val signatureInput = ByteArray(64)

        System.arraycopy(SHAUtil.sha256(canonicalizedProof), 0, signatureInput, 0, 32)
        System.arraycopy(SHAUtil.sha256(canonicalizedDocument), 0, signatureInput, 32, 32)

        println("DOCUMENT")
        println(canonicalizedDocument)
        println("PROOF")
        println(canonicalizedProof)
        val requestPayload = mapOf(
            "input" to Base64.getEncoder().encodeToString(signatureInput),
            "signature_algorithm" to "pkcs1v15",
            "prehashed" to false,
            "signature" to proof.jws
        )

        val response = vaultOperations.write("transit/verify/${query.keyName}/sha2-256", requestPayload)

        TransitVerifyVCResult(response)
    }
}
