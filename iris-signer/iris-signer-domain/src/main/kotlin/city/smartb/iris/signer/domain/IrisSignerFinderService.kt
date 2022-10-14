package city.smartb.iris.signer.domain

import city.smartb.iris.signer.domain.features.GetKeyQueryFunction
import city.smartb.iris.signer.domain.features.SignQueryFunction
import city.smartb.iris.signer.domain.features.VerifyQueryFunction

interface IrisSignerFinderService {
    fun sign(): SignQueryFunction
    fun verify(): VerifyQueryFunction
    fun getKey(): GetKeyQueryFunction
}
