package city.smartb.iris.registrar.service

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
import city.smartb.iris.registrar.domain.DidAggregate
import city.smartb.iris.registrar.domain.DidState
import city.smartb.iris.registrar.domain.features.DidAddPublicKeyCommand
import city.smartb.iris.registrar.domain.features.DidAddPublicKeyEvent
import city.smartb.iris.registrar.domain.features.DidCreateCommand
import city.smartb.iris.registrar.domain.features.DidCreatedEvent
import city.smartb.iris.registrar.domain.features.DidRevokeCommandFunction
import city.smartb.iris.registrar.domain.features.DidRevokePublicKeyCommandFunction
import city.smartb.iris.registrar.entity.DidEntity
import city.smartb.iris.signer.core.IrisSignerService
import city.smartb.iris.signer.domain.features.CreateKeyCommand
import city.smartb.iris.signer.domain.features.GetKeyQuery
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import java.security.PublicKey
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.UUID
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class DidAggregateBean(
	private val didS2Aggregate: DidS2Aggregate,
    private val irisSignerService: IrisSignerService
) : DidAggregate {

	@Bean("createDid")
	override fun createDid() = f2Function<DidCreateCommand, DidCreatedEvent> { cmd ->
		createDid(cmd)
	}

	@Bean("addPublicKey")
	override fun addPublicKey() = f2Function<DidAddPublicKeyCommand, DidAddPublicKeyEvent> { cmd ->
		addPublicKey(cmd)
	}

	@Bean("revokePublicKey")
	override fun revokePublicKey(): DidRevokeCommandFunction = f2Function { cmd ->
		TODO("Not yet implemented")
	}

	@Bean("revoke")
	override fun revoke(): DidRevokePublicKeyCommandFunction = f2Function { cmd ->
		TODO("Not yet implemented")
	}

	suspend fun createDid(cmd: DidCreateCommand): DidCreatedEvent = didS2Aggregate.createWithEvent(cmd,
		{ DidCreatedEvent(s2Id(), DidState(this.state)) }
	) {
		val document = createDIDDocument()
		DidEntity(document = document, state = DidState.Created().position)
	}

	suspend fun addPublicKey(cmd: DidAddPublicKeyCommand): DidAddPublicKeyEvent = didS2Aggregate.doTransition(cmd) {
        this
        val keyId = generateKeyId(cmd.id)

        // TODO check cmd.type
        val pubKey = if (cmd.publicKey.isNullOrBlank()) generateRsaVaultKey(keyId)
            else generateRsaVaultKey(keyId)

        val verificationMethod = buildVerificationMethod(pubKey, keyId, cmd.id)

        this.document.setVerificationMethod(verificationMethod)

		this.state = DidState.Actived().position
		this to DidAddPublicKeyEvent(
			id = cmd.id,
			type = DidState(this.state)
		)
	}

    private suspend fun createDIDDocument(): DIDDocument {
        val did = generateDID()
        val keyId = generateKeyId(did)

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

    private fun generateDID(): String {
        return "did:smartb:${UUID.randomUUID()}"
    }

    private fun generateKeyId(did: String): String {
        return "$did#key-${UUID.randomUUID()}"
    }
}
