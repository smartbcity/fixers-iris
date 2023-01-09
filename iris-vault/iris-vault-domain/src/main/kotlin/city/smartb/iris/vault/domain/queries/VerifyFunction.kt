package city.smartb.iris.vault.domain.queries

import city.smartb.iris.ldproof.VerifiableJsonLd
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias VerifyLibQuery = city.smartb.iris.keypair.domain.VerifyQuery

typealias VerifyFunction = F2Function<VerifyQuery, VerifyResult>


@Serializable
class VerifyQuery(
    @Contextual
    val verifiableJsonLd: VerifiableJsonLd
)


@Serializable
open class VerifyResult(
    val isValid: Boolean
)
