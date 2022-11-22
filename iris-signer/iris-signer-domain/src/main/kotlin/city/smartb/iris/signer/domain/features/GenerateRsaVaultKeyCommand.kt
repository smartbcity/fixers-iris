package city.smartb.iris.signer.domain.features

import f2.dsl.fnc.F2Function
import java.security.interfaces.RSAPublicKey

typealias GenerateRsaVaultKeyCommandFunction = F2Function<GenerateRsaVaultKeyCommand, GenerateRsaVaultKeyCommandResult>

class GenerateRsaVaultKeyCommand(
    val keyId: String
)

class GenerateRsaVaultKeyCommandResult(
    val keyId: String,
    val publicKey: RSAPublicKey
)
