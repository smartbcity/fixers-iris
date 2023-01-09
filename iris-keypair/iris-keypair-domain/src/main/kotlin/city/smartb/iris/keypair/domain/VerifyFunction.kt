package city.smartb.iris.keypair.domain

import city.smartb.iris.ldproof.VerifiableJsonLd
import f2.dsl.fnc.F2Function

typealias VerifyFunction = F2Function<VerifyQuery, VerifyResult>

class VerifyQuery(
    val jsonLd: VerifiableJsonLd,
    val publicKey: String
)

class VerifyResult(
    val isValid: Boolean
)
