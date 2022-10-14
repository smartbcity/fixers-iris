package city.smartb.iris.signer.domain.features

import f2.dsl.fnc.F2Function

typealias SignQueryFunction = F2Function<SignQuery, SignQueryResult>

class SignQuery(
    val keyName: String,
    val issuer: String,
    val subject: Any,
    val id: String,
    val type: String,
)

class SignQueryResult(
    val vc: Any?,
)
