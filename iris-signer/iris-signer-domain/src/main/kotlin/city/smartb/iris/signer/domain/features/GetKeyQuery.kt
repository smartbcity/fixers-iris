package city.smartb.iris.signer.domain.features

import f2.dsl.fnc.F2Function

typealias GetKeyQueryFunction = F2Function<GetKeyQuery, GetKeyQueryResult>

class GetKeyQuery(
    val keyName: String,
    val type: String
)

class GetKeyQueryResult(
    val publicKey: String?
)
