package city.smartb.iris.keypair.domain

import city.smartb.iris.ld.ldproof.VerifiableJsonLd
import f2.dsl.fnc.F2Function

/**
 * Verify the signature of a verifiable json-LD with the provided key.
 * @d2 function
 * @parent [city.smartb.iris.keypair.domain.D2KeypairPage]
 * @order 40
 */
typealias VerifyFunction = F2Function<VerifyQuery, VerifyResult>

/**
 * @d2 query
 * @parent [VerifyFunction]
 */
class VerifyQuery(
    /**
     * The verifiable json-LD to verify.
     */
    val jsonLd: VerifiableJsonLd,

    /**
     * The public key to use to verify the verifiable json-LD.
     */
    val publicKey: String
)

/**
 * @d2 result
 * @parent [VerifyFunction]
 */
class VerifyResult(
    /**
     * True if the verifiable json-LD is valid .
     * False otherwise.
     */
    val isValid: Boolean
)
