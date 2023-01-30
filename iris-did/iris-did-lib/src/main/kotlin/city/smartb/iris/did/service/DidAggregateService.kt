package city.smartb.iris.did.service

import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64
import city.smartb.iris.did.domain.DidState
import city.smartb.iris.did.domain.commands.DidCreateCommand
import city.smartb.iris.did.domain.commands.DidCreatedEvent
import city.smartb.iris.did.domain.commands.DidProofUpdateCommand
import city.smartb.iris.did.domain.commands.DidProofUpdatedEvent
import city.smartb.iris.did.domain.commands.DidUpdateCommand
import city.smartb.iris.did.domain.commands.DidUpdatedEvent
import city.smartb.iris.did.domain.commands.DidVerificationMethodAddCommand
import city.smartb.iris.did.domain.commands.DidVerificationMethodAddedEvent
import city.smartb.iris.did.s2.config.DidS2Aggregate
import city.smartb.iris.did.s2.entity.DidEntity
import city.smartb.iris.ld.did.DIDDocument
import city.smartb.iris.ld.did.DIDDocumentBuilder
import city.smartb.iris.ld.did.DIDVerificationMethod
import city.smartb.iris.ld.did.DIDVerificationMethodBuilder
import java.security.PublicKey
import java.security.interfaces.RSAPublicKey
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class DidAggregateService(
    private val didS2Aggregate: DidS2Aggregate
) {
    suspend fun create(command: DidCreateCommand): DidCreatedEvent {
        return didS2Aggregate.createWithEvent(command,
            { DidCreatedEvent(s2Id(), DidState(this.state), document) }
        ) {
            DidEntity(document = createDIDDocument(), state = DidState.Created().position)
        }
    }

    suspend fun verificationMethodAdd(cmd: DidVerificationMethodAddCommand):
            DidVerificationMethodAddedEvent = didS2Aggregate.doTransition(cmd) {

        val pubKey = getPublicKey(cmd.publicKey, cmd.type)
        val keyId = "${cmd.id}#${cmd.keyId}"

        val verificationMethod = DIDVerificationMethodBuilder
            .create()
            .withId(keyId)
            .withController(cmd.controller)
            .withType(cmd.type)
            .withPublicKey(pubKey)
            .build()

        this.document.addVerificationMethod(verificationMethod)

        // Add purpose
//        this.addPurpose(cmd.purpose, keyId)

        this.state = DidState.Created().position
        this to DidVerificationMethodAddedEvent(
            id = cmd.id,
            type = DidState(this.state),
            document = this.document
        )
    }

    suspend fun update(cmd: DidUpdateCommand): DidUpdatedEvent = didS2Aggregate.doTransition(cmd) {
        this.document = cmd.document
        this to DidUpdatedEvent(
            id = cmd.id,
            type = DidState(this.state),
            document = this.document
        )
    }

    suspend fun updateProof(cmd: DidProofUpdateCommand): DidProofUpdatedEvent = didS2Aggregate.doTransition(cmd) {
        this.document.setProof(cmd.proof)
        this to DidProofUpdatedEvent(
            id = cmd.id,
            type = DidState(this.state),
            document = this.document
        )
    }



    private fun generateDID(): String {
        return "did:smartb:${UUID.randomUUID()}"
    }

    private fun createDIDDocument(): DIDDocument {
        val did = generateDID()

        val vcBuild = DIDDocumentBuilder
            .create()
            .withId(did)
            .withController(did)
            .withServices(emptyList())
            .withVerificationMethods(emptyList())
            .withAuthentications(emptyList())
            .withCapabilityDelegations(emptyList())
            .withCapabilityInvocations(emptyList())
            .withAssertionMethods(emptyList())
            .withKeyAgreements(emptyList())

        return DIDDocument(vcBuild.asJson())
    }

    private fun getPublicKey(publicKey: String, type: String): PublicKey {
        return when (type) {
            DIDVerificationMethod.RSA_VERIFICATION_2018 -> parsePublicKey(publicKey)
            else -> { throw Exception("Error parsing public key")}
        }
    }

    private fun parsePublicKey(pubKey: String): RSAPublicKey {
        return try {
            RSAKeyPairDecoderBase64.decodePublicKey(pubKey)
        } catch (e: Exception) {
            RSAKeyPairDecoderBase64.decodePublicKey(pemPublicKeyToString(pubKey))
        }
    }

    private fun pemPublicKeyToString(pemString: String): String {
        return pemString
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace(System.lineSeparator(), "")
            .replace("-----END PUBLIC KEY-----", "")
    }
}
