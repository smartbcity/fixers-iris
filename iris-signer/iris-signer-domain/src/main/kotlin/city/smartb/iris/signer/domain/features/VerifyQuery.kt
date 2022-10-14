package city.smartb.iris.signer.domain.features

import city.smartb.iris.vc.VerifiableCredential
import f2.dsl.fnc.F2Function

typealias VerifyQueryFunction = F2Function<VerifyQuery, VerifyQueryResult>

class VerifyQuery(
    val vc: VerifiableCredential
)

class VerifyQueryResult(
    val verified: Boolean?
)
