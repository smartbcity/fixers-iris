package city.smartb.iris.signer.domain.features

import city.smartb.iris.ldproof.VerifiableJsonLd
import f2.dsl.fnc.F2Function

typealias VerifyQueryFunction = F2Function<VerifyQuery, VerifyQueryResult>

class VerifyQuery(
    val jsonLd: VerifiableJsonLd
)

class VerifyQueryResult(
    val isValid: Boolean
)
