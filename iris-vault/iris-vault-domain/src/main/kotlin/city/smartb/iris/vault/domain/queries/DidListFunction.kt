package city.smartb.iris.vault.domain.queries

import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual

/**
 * Fetch all DID Documents.
 * @d2 function
 * @parent [city.smartb.iris.vault.domain.D2IrisVaultPage]
 * @order 40
 */
typealias DidListFunction = F2Function<DidListQuery, DidListResult>
typealias DidListLibQuery = city.smartb.iris.did.domain.queries.DidListQuery

/**
 * @parent [DidListFunction]
 * @d2 query
 */
//@Serializable
class DidListQuery

/**
 * @parent [DidListFunction]
 * @d2 result
 */
//@Serializable
open class DidListResult(
    /**
     * List of DID Documents.
     */
    @Contextual
    val didDocuments: List<DIDDocument>
)
