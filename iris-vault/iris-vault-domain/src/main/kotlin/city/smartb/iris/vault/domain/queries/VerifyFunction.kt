package city.smartb.iris.vault.domain.queries

import city.smartb.iris.ld.ldproof.VerifiableJsonLd
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Verify a Verifiable json-LD.
 * @d2 function
 * @parent [city.smartb.iris.vault.domain.D2IrisVaultPage]
 * @order 60
 */
typealias VerifyFunction = F2Function<VerifyQuery, VerifyResult>
typealias VerifyLibQuery = city.smartb.iris.keypair.domain.VerifyQuery

/**
 * @d2 query
 * @parent [VerifyFunction]
 */
@Serializable
class VerifyQuery(
    /**
     * The verifiable json-LD to verify.
     */
    @Contextual
    val verifiableJsonLd: VerifiableJsonLd
)

/**
 * @d2 result
 * @parent [VerifyFunction]
 */
@Serializable
open class VerifyResult(
    /**
     * True if the verifiable json-LD signature is valid using the public key specified in its proof.
     * False otherwise.
     */
    val isValid: Boolean
)
