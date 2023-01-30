package city.smartb.iris.did.domain.queries

import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.F2Function

/**
 * Get all DID Documents.
 * @d2 function
 * @parent [city.smartb.iris.did.domain.D2DidPage]
 * @order 70
 */
typealias DidListFunction = F2Function<DidListQuery, DidListEvent>

/**
 * @d2 query
 * @parent [DidListFunction]
 */
class DidListQuery

/**
 * @d2 result
 * @parent [DidListFunction]
 */
class DidListEvent(
    val documents: List<DIDDocument>
)
