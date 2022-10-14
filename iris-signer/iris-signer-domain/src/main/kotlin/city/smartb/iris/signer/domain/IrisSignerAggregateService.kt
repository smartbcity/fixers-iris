package city.smartb.iris.signer.domain

import city.smartb.iris.signer.domain.features.CreateKeyCommandFunction

interface IrisSignerAggregateService {
    fun createKey(): CreateKeyCommandFunction
}
