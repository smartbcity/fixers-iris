package city.smartb.iris.signer.domain

import city.smartb.iris.signer.domain.features.CreateKeyCommandFunction
import city.smartb.iris.signer.domain.features.GenerateRsaVaultKeyCommandFunction

interface IrisSignerAggregateService {
    fun createKey(): CreateKeyCommandFunction

    fun generateRsaVaultKey(): GenerateRsaVaultKeyCommandFunction
}
