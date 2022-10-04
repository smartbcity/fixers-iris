package city.smartb.iris.signer.core.features.transit

import city.smartb.iris.ldproof.LdProofBuilder
import city.smartb.iris.ldproof.util.CanonicalizationUtil
import city.smartb.iris.ldproof.util.SHAUtil
import city.smartb.iris.signer.core.utils.getSignatureValue
import city.smartb.iris.vc.VerifiableCredentialBuilder
import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import java.time.LocalDateTime
import java.util.Base64
import java.util.UUID
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.vault.core.VaultOperations


typealias TransitSignPayloadCommandFunction = F2Function<TransitSignPayloadCommand, TransitSignPayloadCommandResult>

class TransitSignPayloadCommand(
    val keyName: String,
    val issuer: String,
    val subject: Any,
    val id: String,
)

class TransitSignPayloadCommandResult(
    val vc: Any?,
)

@Configuration
open class TransitSignPayloadCommandFunctionImpl(
    private val vaultOperations: VaultOperations,
) {
    @Bean
    open fun <T> transitSignPayloadCommandFunction(): TransitSignPayloadCommandFunction = f2Function { query ->
        val created = LocalDateTime.now()
        val nonce = UUID.randomUUID().toString()

        val vcBuild = VerifiableCredentialBuilder
            .create<T>()
            .withContextDefault()
            .withId(query.id)
            .withType("VerifiableCredential")
            .withIssuanceDate(created)
            .withIssuer(query.issuer)
            .withCredentialSubject(query.subject as T)

        val proofBuilder = LdProofBuilder.builder()
            .withCreated(created)
            .withChallenge(nonce)
            .withProofPurpose("assertionMethod")
            .withVerificationMethod("${query.issuer}#${query.keyName}") // Improve
            .withType("RsaSignature2018")
            .withContextDefault()

        val canonicalizedProof = proofBuilder.canonicalize()
        val canonicalizedDocument = CanonicalizationUtil.buildCanonicalizedDocument(vcBuild.asJson())

        println("DOCUMENT")
        println(canonicalizedDocument)
        println("PROOF")
        println(canonicalizedProof)

        val signatureInput = ByteArray(64)
        System.arraycopy(SHAUtil.sha256(canonicalizedProof), 0, signatureInput, 0, 32)
        System.arraycopy(SHAUtil.sha256(canonicalizedDocument), 0, signatureInput, 32, 32)

        val requestPayload = mapOf(
            "input" to Base64.getEncoder().encodeToString(signatureInput),
            "signature_algorithm" to "pkcs1v15",
            "prehashed" to false,
        )

        val response = vaultOperations.write("transit/sign/${query.keyName}/sha2-256", requestPayload)

        val signature = response!!.getSignatureValue()

        val proof = proofBuilder.build(signature)

        TransitSignPayloadCommandResult(vcBuild.build(proof).asJson())
    }
}
