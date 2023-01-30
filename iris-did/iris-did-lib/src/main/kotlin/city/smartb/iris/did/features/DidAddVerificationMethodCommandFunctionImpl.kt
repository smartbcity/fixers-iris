//package city.smartb.iris.did.features
//
//import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64
//import city.smartb.iris.did.DIDDocument
//import city.smartb.iris.did.DIDVerificationMethodBuilder
//import city.smartb.iris.did.model.DIDVerificationMethod
//import city.smartb.iris.did.signer.DidSigner
//import city.smartb.iris.s2.config.DidS2Aggregate
//import city.smartb.iris.did.domain.DidState
//import city.smartb.iris.did.domain.commands.DidAddVerificationMethodCommand
//import city.smartb.iris.did.domain.commands.DidAddVerificationMethodCommandFunction
//import city.smartb.iris.did.domain.commands.DidAddVerificationMethodEvent
//import city.smartb.iris.s2.entity.DidEntity
//import f2.dsl.fnc.f2Function
//import java.security.PublicKey
//import java.security.interfaces.RSAPublicKey
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
//open class DidAddVerificationMethodCommandFunctionImpl(
//    private val didSigner: DidSigner,
//    private val didS2Aggregate: DidS2Aggregate
//) {
//
//    @Bean
//    open fun didAddVerificationMethodCommandFunction(): DidAddVerificationMethodCommandFunction = f2Function { cmd ->
//        addVerificationMethod(cmd)
//    }
//
//    private suspend fun addVerificationMethod(cmd: DidAddVerificationMethodCommand):
//            DidAddVerificationMethodEvent = didS2Aggregate.doTransition(cmd) {
//
//        val pubKey = getPublicKey(cmd.publicKey, cmd.type)
//        val keyId = "${cmd.id}#${cmd.keyId}"
//
//        val verificationMethod = DIDVerificationMethodBuilder
//            .create()
//            .withId(keyId)
//            .withController(cmd.controller)
//            .withType(cmd.type)
//            .withPublicKey(pubKey)
//            .build()
//
//        this.document.addVerificationMethod(verificationMethod)
//
//        // Add purpose
//        this.addPurpose(cmd.purpose, keyId)
//
//        this.state = DidState.Created().position
//
//        this.document = didSigner.sign(this.document)
//
//        this to DidAddVerificationMethodEvent(
//            id = cmd.id,
//            type = DidState(this.state),
//            document = this.document.asJson()
//        )
//    }
//
//    private fun DidEntity.addPurpose(purposes: List<String>, keyId: String) {
//        if (purposes.contains(DIDDocument.JSON_LD_ASSERTION_METHOD)) {
//            this.document.addAssertionMethod(keyId)
//        }
//
//        if (purposes.contains(DIDDocument.JSON_LD_AUTHENTICATION)) {
//            this.document.addAuthentication(keyId)
//        }
//
//        if (purposes.contains(DIDDocument.JSON_LD_CAPABILITY_DELEGATION)) {
//            this.document.addCapabilityDelegation(keyId)
//        }
//
//        if (purposes.contains(DIDDocument.JSON_LD_CAPABILITY_INVOCATION)) {
//            this.document.addCapabilityInvocation(keyId)
//        }
//
//        if (purposes.contains(DIDDocument.JSON_LD_KEY_AGREEMENT)) {
//            this.document.addKeyAgreement(keyId)
//        }
//    }
//
//    private fun getPublicKey(publicKey: String, type: String): PublicKey {
//        return when (type) {
//            DIDVerificationMethod.RSA_VERIFICATION_2018 -> parsePublicKey(publicKey)
//            else -> { throw Exception("Error parsing public key")}
//        }
//    }
//
//    private fun parsePublicKey(pubKey: String): RSAPublicKey {
//        return try {
//            RSAKeyPairDecoderBase64.decodePublicKey(pubKey)
//        } catch (e: Exception) {
//            RSAKeyPairDecoderBase64.decodePublicKey(pemPublicKeyToString(pubKey))
//        }
//    }
//
//    private fun pemPublicKeyToString(pemString: String): String {
//        return pemString
//            .replace("-----BEGIN PUBLIC KEY-----", "")
//            .replace(System.lineSeparator(), "")
//            .replace("-----END PUBLIC KEY-----", "")
//    }
//}
