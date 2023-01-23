package city.smartb.iris.vault.domain.queries

import city.smartb.iris.did.domain.DidId
import city.smartb.iris.ld.jsonld.JsonLdObject
import city.smartb.iris.ld.ldproof.VerifiableJsonLd
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Sign any Json LD with the specified transit key.
 * @d2 function
 * @parent [city.smartb.iris.vault.domain.D2IrisVaultPage]
 * @order 50
 */
typealias SignFunction = F2Function<SignQuery, SignResult>
typealias SignLibQuery = city.smartb.iris.keypair.domain.SignQuery

/**
 * @parent [SignFunction]
 * @d2 query
 */
@Serializable
class SignQuery(
    /**
     * The DID of the owner of the transit key.
     */
    val did: DidId,

    /**
     * The json-LD to sign.
     */
    @Contextual
    val jsonLd: JsonLdObject,

    /**
     * The name of the transit key.
     */
    val privateKeyName: String
)

/**
 * @parent [SignFunction]
 * @d2 result
 */
@Serializable
open class SignResult(
    /**
     * The json-LD with its proof attached.
     */
    @Contextual
    val verifiableJsonLd: VerifiableJsonLd
)
