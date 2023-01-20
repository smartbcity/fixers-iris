package city.smartb.iris.vault.lib.service

import city.smartb.iris.crypto.rsa.RSAKeyPairReader
import city.smartb.iris.did.DidFeaturesImpl
import city.smartb.iris.did.domain.commands.DidProofUpdateCommand
import city.smartb.iris.did.domain.commands.DidVerificationMethodAddCommand
import city.smartb.iris.keypair.domain.KeypairCreateCommand
import city.smartb.iris.keypair.domain.SignQuery
import city.smartb.iris.keypair.lib.KeypairFeaturesImpl
import city.smartb.iris.ld.did.DIDDocument
import city.smartb.iris.ld.did.DIDVerificationMethod
import city.smartb.iris.vault.domain.commands.DidCreateCommand
import city.smartb.iris.vault.domain.commands.DidCreatedEvent
import city.smartb.iris.vault.domain.commands.DidLibCreateCommand
import city.smartb.iris.vault.domain.commands.DidPublicKeyAddCommand
import city.smartb.iris.vault.domain.commands.DidPublicKeyAddedEvent
import f2.dsl.fnc.invoke
import java.util.UUID
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger

@Service
class IrisVaultAggregateService(
    private val didFeatures: DidFeaturesImpl,
    private val keypairFeatures: KeypairFeaturesImpl,
) {
    private val logger by Logger()

    suspend fun didCreate(cmd: DidCreateCommand): DidCreatedEvent {
        logger.debug("didCreate: $cmd")
        var didDocument = didFeatures.didCreate().invoke(DidLibCreateCommand()).document

        val keyId = generateId()
        val publicKey = keypairFeatures.keypairCreate().invoke(KeypairCreateCommand(keyId)).publicKey

        didDocument = didFeatures.didVerificationMethodAdd().invoke(DidVerificationMethodAddCommand(
            id = didDocument.id,
            keyId = keyId,
            type = DIDVerificationMethod.RSA_VERIFICATION_2018,
            controller = didDocument.id,
            publicKey = publicKey
        )).document

        return DidCreatedEvent(
            didDocument = didDocument.updateProof()
        )
    }

    suspend fun didPublicKeyAdd(cmd: DidPublicKeyAddCommand): DidPublicKeyAddedEvent {
        logger.debug("didPublicKeyAdd: $cmd")
        val keyName = generateId()
        val publicKey = keypairFeatures.keypairCreate().invoke(KeypairCreateCommand(keyName)).publicKey
        val didDocument = didFeatures.didVerificationMethodAdd().invoke(DidVerificationMethodAddCommand(
            id = cmd.did,
            keyId = keyName,
            type = DIDVerificationMethod.RSA_VERIFICATION_2018,
            controller = cmd.did,
            publicKey = publicKey
        )).document

        return DidPublicKeyAddedEvent(didDocument)
    }

    private suspend fun DIDDocument.updateProof(): DIDDocument {
        val keyPair = RSAKeyPairReader.loadKeyPair("server")

        val proof = keypairFeatures.sign().invoke(SignQuery(
            jsonLd = this,
            privateKey = keyPair.private,
            method = "rsa",
            pathToVerificationKey = "pathToServerPubKey"
        )).verifiableJsonLd.proof

        return didFeatures.didProofUpdate().invoke(DidProofUpdateCommand(
            id = this.id,
            proof = proof
        )).document
    }

    private fun generateId(): String = UUID.randomUUID().toString()
}
