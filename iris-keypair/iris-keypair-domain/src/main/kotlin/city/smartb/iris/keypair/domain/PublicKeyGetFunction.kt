package city.smartb.iris.keypair.domain

import f2.dsl.fnc.F2Function

/**
 * Get the public key of a transit key.
 * @d2 function
 * @parent [city.smartb.iris.keypair.domain.D2KeypairPage]
 * @order 20
 */
typealias PublicKeyGetFunction = F2Function<PublicKeyGetQuery, PublicKeyGetResult>

/**
 * @d2 query
 * @parent [PublicKeyGetFunction]
 */
class PublicKeyGetQuery(
    /**
     * The name of the transit key to retrieve.
     */
    val keyName: String
)

/**
 * @d2 result
 * @parent [PublicKeyGetFunction]
 */
class PublicKeyGetResult(
    /**
     * The value of the public key of the transit key.
     */
    val publicKey: String
)
