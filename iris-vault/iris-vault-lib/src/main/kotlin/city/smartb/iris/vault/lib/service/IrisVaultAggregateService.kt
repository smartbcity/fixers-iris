package city.smartb.iris.vault.lib.service

import city.smartb.iris.did.DidFeaturesImpl
import city.smartb.iris.did.domain.commands.DidProofUpdateCommand
import city.smartb.iris.did.domain.commands.DidVerificationMethodAddCommand
import city.smartb.iris.did.model.DIDVerificationMethod
import city.smartb.iris.keypair.domain.KeypairCreateCommand
import city.smartb.iris.keypair.domain.SignQuery
import city.smartb.iris.keypair.lib.KeypairFeaturesImpl
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

        println("1")
        println(didDocument)
        // use did lib to init a did document
        val keyId = generateId()
        val publicKey = keypairFeatures.keypairCreate().invoke(KeypairCreateCommand(keyId)).publicKey
        // use keypair lib to generate a transit key

        didDocument = didFeatures.didVerificationMethodAdd().invoke(DidVerificationMethodAddCommand(
            id = didDocument.id,
            keyId = keyId,
            type = DIDVerificationMethod.RSA_VERIFICATION_2018,
            controller = didDocument.id,
            publicKey = publicKey
        )).document
        // use did lib to add the newly created key to the did document

        println("2")
        println(didDocument)

        val proof = keypairFeatures.sign().invoke(SignQuery(
            jsonLd = didDocument,
            privateKey = keyId,
            method = "transit",
            pathToVerificationKey = "pathtokey"
        )).verifiableJsonLd.proof
        // use keypair lib to sign the did document

        didDocument = didFeatures.didProofUpdate().invoke(DidProofUpdateCommand(
            id = didDocument.id,
            proof = proof
        )).document
        // use did lib to add the proof to the did document and store in the registry

        println("3")
        println(didDocument)

        return DidCreatedEvent(
            didDocument = didDocument
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

    private fun generateId(): String = UUID.randomUUID().toString()
}
