package city.smartb.iris.vault.features

import city.smartb.iris.signer.core.IrisSignerService
import city.smartb.iris.signer.domain.features.GenerateRsaVaultKeyCommand
import city.smartb.iris.vault.domain.commands.PrivateKeyGenerateCommand
import city.smartb.iris.vault.domain.commands.PrivateKeyGenerateCommandFunction
import city.smartb.iris.vault.domain.commands.PrivateKeyGeneratedEvent
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import f2.dsl.fnc.f2Function
import f2.dsl.fnc.invoke
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class PrivateKeyGenerateCommandFunctionImpl(
    private val irisSignerService: IrisSignerService,
) {

    @Bean
    open fun privateKeyGenerateCommandFunction(): PrivateKeyGenerateCommandFunction = f2Function { cmd ->
        generatePrivateKey(cmd)
    }

    private fun generatePrivateKey(cmd: PrivateKeyGenerateCommand): PrivateKeyGeneratedEvent = runBlocking {
        val publicRSAKey = irisSignerService.generateRsaVaultKey().invoke(
            GenerateRsaVaultKeyCommand(keyId = cmd.name)
        ).publicKey

        val jwk: JWK = RSAKey.Builder(publicRSAKey)
            .keyUse(KeyUse.SIGNATURE)
            .build()

        PrivateKeyGeneratedEvent(
            publicKey = jwk.toPublicJWK().toJSONObject()
        )
    }
}
