package city.smartb.iris.vault.features

import city.smartb.iris.crypto.hc.vault.kv.signer.VaultKvSigner
import city.smartb.iris.jsonld.JsonLdObject
import city.smartb.iris.ldproof.LdJsonObjectBuilder
import city.smartb.iris.ldproof.LdProof
import city.smartb.iris.ldproof.LdProofBuilder
import city.smartb.iris.ldproof.crypto.RsaSignature2018LdProofSigner
import city.smartb.iris.signer.core.IrisSignerService
import city.smartb.iris.vault.domain.commands.SignCommand
import city.smartb.iris.vault.domain.commands.SignCommandFunction
import city.smartb.iris.vault.domain.commands.SignedEvent
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import java.time.LocalDateTime
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.vault.core.VaultOperations

@Configuration
open class SignCommandFunctionImpl(
    private val vaultOperations: VaultOperations
) {

    @Bean
    open fun signCommandFunction(): SignCommandFunction = f2Function { cmd ->
        sign(cmd)
    }

    private fun sign(cmd: SignCommand): SignedEvent = runBlocking {
        val created = LocalDateTime.now()
        val nonce: String = UUID.randomUUID().toString()

        val signer = VaultKvSigner(cmd.keyName, vaultOperations)

        val proofBuilder = LdProofBuilder.builder()
            .withCreated(created)
            .withChallenge(nonce)
            .withProofPurpose("assertionMethod")
            .withVerificationMethod("")


        val ldSigner = RsaSignature2018LdProofSigner(signer, proofBuilder)
        val builder = LdJsonObjectBuilder.builder(cmd.payload)
        val proof = ldSigner.sign(builder)

        val jsonLd = JsonLdObject(cmd.payload).asJson()
        jsonLd.put(LdProof.JSON_LD_PROOF, proof)

        SignedEvent(jsonLd)
    }
}
