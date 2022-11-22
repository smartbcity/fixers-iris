package city.smartb.iris.registrar.features

import city.smartb.iris.did.DIDDocument
import city.smartb.iris.did.DIDDocumentBuilder
import city.smartb.iris.did.DIDVerificationMethodBuilder
import city.smartb.iris.did.model.DIDAuthentication
import city.smartb.iris.did.model.DIDVerificationMethod
import city.smartb.iris.ldproof.LdProofBuilder
import city.smartb.iris.registrar.signer.IrisRegistrarSigner
import city.smartb.iris.s2.config.DidS2Aggregate
import city.smartb.iris.s2.domain.DidState
import city.smartb.iris.s2.domain.commands.DidCreateCommand
import city.smartb.iris.s2.domain.commands.DidCreateCommandFunction
import city.smartb.iris.s2.domain.commands.DidCreatedEvent
import city.smartb.iris.s2.entity.DidEntity
import city.smartb.iris.signer.core.IrisSignerService
import city.smartb.iris.signer.domain.features.GenerateRsaVaultKeyCommand
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import java.util.UUID
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class DidCreateCommandFunctionImpl(
    private val irisSignerService: IrisSignerService,
    private val didS2Aggregate: DidS2Aggregate,
) {

    @Bean
    open fun didCreateCommandFunction(): DidCreateCommandFunction = f2Function { cmd ->
        createDid(cmd)
    }

    private suspend fun createDid(cmd: DidCreateCommand): DidCreatedEvent {
        val document = createDIDDocument()

        return didS2Aggregate.createWithEvent(cmd,
            { DidCreatedEvent(s2Id(), DidState(this.state), document.asJson()) }
        ) {
            DidEntity(document = document, state = DidState.Created().position)
        }
    }

    private suspend fun createDIDDocument(): DIDDocument {
        val did = generateDID()
        val keyId = IrisRegistrarSigner().generateKeyId(did)

        val publicRSAKey = irisSignerService.generateRsaVaultKey().invoke(GenerateRsaVaultKeyCommand(
            keyId = keyId
        )).publicKey

        val verificationMethod = DIDVerificationMethodBuilder
            .create()
            .withId(keyId)
            .withController(did)
            .withType(DIDVerificationMethod.RSA_VERIFICATION_2018)
            .withPublicKey(publicRSAKey)
            .build()

        val vcBuild = DIDDocumentBuilder
            .create()
            .withId(did)
            .withController(did)
            .withVerificationMethod(verificationMethod)
            .withAuthentication(buildDIDAuthenticationFromKeyId(keyId))

        val proofBuilder = LdProofBuilder.builder()
            .withChallenge("Challenges")
            .withCreatedNow()
            .withDomain("smartb.city")
            .withProofPurpose("assertionMethod")
            .withVerificationMethod("TODO: urlToPubKey")

        return IrisRegistrarSigner().sign(vcBuild, proofBuilder)
    }

    // TODO handles build from DIDPublicKey
    private fun buildDIDAuthenticationFromKeyId(keyId: String): DIDAuthentication {
        return DIDAuthentication(keyId)
    }

    private fun generateDID(): String {
        return "did:smartb:${UUID.randomUUID()}"
    }
}
