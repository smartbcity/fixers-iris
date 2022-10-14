package city.smartb.iris.registrar.features

import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64
import city.smartb.iris.crypto.rsa.RSAKeyPairReader
import city.smartb.iris.crypto.rsa.signer.RS256Signer
import city.smartb.iris.did.DIDDocument
import city.smartb.iris.did.DIDDocumentBuilder
import city.smartb.iris.did.DIDSigner
import city.smartb.iris.did.DIDVerificationMethodBuilder
import city.smartb.iris.did.model.DIDAuthentication
import city.smartb.iris.did.model.DIDVerificationMethod
import city.smartb.iris.ldproof.LdProofBuilder
import city.smartb.iris.registrar.config.DidS2Aggregate
import city.smartb.iris.registrar.domain.DidState
import city.smartb.iris.registrar.domain.features.DidCreateCommand
import city.smartb.iris.registrar.domain.features.DidCreatedEvent
import city.smartb.iris.registrar.entity.DidEntity
import city.smartb.iris.registrar.model.DIDCreateOptions
import city.smartb.iris.signer.core.IrisSignerService
import city.smartb.iris.signer.domain.features.CreateKeyCommand
import city.smartb.iris.signer.domain.features.GetKeyQuery
import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import java.security.PublicKey
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.UUID
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

typealias CreateDIDQueryFunction = F2Function<CreateDIDQuery, CreateDIDQueryResult>

class CreateDIDQuery(
    val method: String?,
    val did: String?,
    val options: DIDCreateOptions?,
//    val secret: , TODO Implement
    val didDocument: DIDDocument?
)

class CreateDIDQueryResult(
    val document: Any?
)

@Configuration
open class CreateDIDQueryFunctionImpl(
    private val irisSignerService: IrisSignerService,
    private val didS2Aggregate: DidS2Aggregate,
) {

//    @Bean("createDid")
    open fun createDid() = f2Function<DidCreateCommand, DidCreatedEvent> { cmd ->
        createDid(cmd)
    }

    suspend fun createDid(cmd: DidCreateCommand): DidCreatedEvent = didS2Aggregate.createWithEvent(cmd,
        { DidCreatedEvent(s2Id(), DidState(this.state)) }
    ) {
        val document = createDIDDocument()
        DidEntity(document = document, state = DidState.Created().position)
    }

    private suspend fun createDIDDocument(): DIDDocument {
        val did = "did:smartb:${UUID.randomUUID()}"
        val keyId = "$did#key-${UUID.randomUUID()}"

        val publicRSAKey = generateRsaVaultKey(keyId)

        val vcBuild = DIDDocumentBuilder
            .create()
            .withId(did)
            .withController(did)
            .withVerificationMethod(buildVerificationMethod(publicRSAKey, keyId, did))
            .withAuthentication(buildDIDAuthenticationFromKeyId(keyId))

        val proofBuilder = LdProofBuilder.builder()
            .withChallenge("Challenges")
            .withCreatedNow()
            .withDomain("smartb.city")
            .withProofPurpose("assertionMethod")
            .withVerificationMethod("TODO: urlToPubKey")

        return signDIDDocument(vcBuild, proofBuilder)
    }

    // TODO generalize to generate multiple key types
    private suspend fun generateRsaVaultKey(keyId: String): RSAPublicKey {
        val createCommand = CreateKeyCommand(
            keyName = keyId,
            type = "HcVaultKV"
        )
        irisSignerService.createKey().invoke(createCommand)

        val getQuery = GetKeyQuery(
            keyName = keyId,
            type = "HcVaultKV"
        )
        val response = irisSignerService.getKey().invoke(getQuery)

        return RSAKeyPairDecoderBase64.decodePublicKey(response.publicKey)
    }

    private fun buildVerificationMethod(key: PublicKey, keyId: String, controller: String): DIDVerificationMethod {
        return DIDVerificationMethodBuilder
            .create()
            .withId(keyId)
            .withController(controller)
            .withType(DIDVerificationMethod.RSA_VERIFICATION_2018)
            .withPublicKey(key)
            .build();
    }

    // TODO handles build from DIDPublicKey
    private fun buildDIDAuthenticationFromKeyId(keyId: String): DIDAuthentication {
        return DIDAuthentication(keyId)
    }

    private fun signDIDDocument(vcBuild: DIDDocumentBuilder, proofBuilder: LdProofBuilder): DIDDocument {
        val vcSign = DIDSigner()
        val pair = RSAKeyPairReader.loadKeyPair("server")
        val signer = RS256Signer((pair.private as RSAPrivateKey)!!)

        return vcSign.sign(vcBuild, proofBuilder, signer)
    }
}
