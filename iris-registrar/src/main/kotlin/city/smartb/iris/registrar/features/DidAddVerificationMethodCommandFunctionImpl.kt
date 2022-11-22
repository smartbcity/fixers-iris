package city.smartb.iris.registrar.features

import city.smartb.iris.crypto.rsa.RSAKeyPairDecoderBase64
import city.smartb.iris.did.DIDVerificationMethodBuilder
import city.smartb.iris.did.model.DIDVerificationMethod
import city.smartb.iris.registrar.signer.IrisRegistrarSigner
import city.smartb.iris.s2.config.DidS2Aggregate
import city.smartb.iris.s2.domain.DidState
import city.smartb.iris.s2.domain.commands.DidAddVerificationMethodCommand
import city.smartb.iris.s2.domain.commands.DidAddVerificationMethodCommandFunction
import city.smartb.iris.s2.domain.commands.DidAddVerificationMethodEvent
import city.smartb.iris.signer.core.IrisSignerService
import city.smartb.iris.signer.domain.features.GenerateRsaVaultKeyCommand
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import java.security.interfaces.RSAPublicKey
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class DidAddVerificationMethodCommandFunctionImpl(
    private val irisSignerService: IrisSignerService,
    private val didS2Aggregate: DidS2Aggregate,
) {

    @Bean
    open fun didAddVerificationMethodCommandFunction(): DidAddVerificationMethodCommandFunction = f2Function { cmd ->
        addVerificationMethod(cmd)
    }

    private suspend fun addVerificationMethod(cmd: DidAddVerificationMethodCommand):
            DidAddVerificationMethodEvent = didS2Aggregate.doTransition(cmd) {
        val keyId = IrisRegistrarSigner().generateKeyId(cmd.id)
        val pubKey = getPublicKey(cmd, keyId)
        val verificationMethod = DIDVerificationMethodBuilder
            .create()
            .withId(keyId)
            .withController(cmd.id)
            .withType(DIDVerificationMethod.RSA_VERIFICATION_2018)
            .withPublicKey(pubKey)
            .build()

        this.document.addVerificationMethod(verificationMethod)

        this.state = DidState.Created().position

        this.document = IrisRegistrarSigner().renewRegistrarProof(this.document)

        this to DidAddVerificationMethodEvent(
            id = cmd.id,
            type = DidState(this.state),
            document = this.document.asJson()
        )
    }

    private suspend fun getPublicKey(cmd: DidAddVerificationMethodCommand, keyId: String): RSAPublicKey {
        return if (cmd.publicKey.isNullOrBlank()) {
            irisSignerService.generateRsaVaultKey()
                .invoke(GenerateRsaVaultKeyCommand(keyId = keyId)).publicKey
        }
        else {
            parsePublicKey(cmd.publicKey!!)
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
