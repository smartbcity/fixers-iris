package city.smartb.iris.keypair.domain

import f2.dsl.fnc.F2Function

typealias PublicKeyGetFunction = F2Function<PublicKeyGetQuery, PublicKeyGetResult>

class PublicKeyGetQuery(
    val keyName: String,
    val method: String
)

class PublicKeyGetResult(
    val publicKey: String
)
