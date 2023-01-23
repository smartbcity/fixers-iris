package city.smartb.iris.vault.domain.queries

import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Fetch a DID Document by its DID.
 * @d2 function
 * @parent [city.smartb.iris.vault.domain.D2IrisVaultPage]
 * @order 30
 */
typealias DidGetFunction = F2Function<DidGetQuery, DidGetResult>
typealias DidGetLibQuery = city.smartb.iris.did.domain.queries.DidGetQuery

/**
 * @parent [DidGetFunction]
 * @d2 query
 */
@Serializable
class DidGetQuery(
    /**
     * The DID of the DID Document to fetch.
     */
    @Contextual
    val did: String
)

/**
 * @parent [DidGetFunction]
 * @d2 result
 */
@Serializable
open class DidGetResult(
    /**
     * The fetched DID Document.
     */
    @Contextual
    val didDocument: DIDDocument
)
