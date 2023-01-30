package city.smartb.iris.did.domain.queries

import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.F2Function

/**
 * Get a DID Document by its DID.
 * @d2 function
 * @parent [city.smartb.iris.did.domain.D2DidPage]
 * @order 70
 */
typealias DidGetFunction = F2Function<DidGetQuery, DidGetEvent>

/**
 * @d2 query
 * @parent [DidGetFunction]
 */
class DidGetQuery(
    /**
     * The DID of the DID Document to fetch.
     */
    val id: DidId
)

/**
 * @d2 result
 * @parent [DidGetFunction]
 */
class DidGetEvent(
    /**
     * The DID of the fetched DID Document.
     */
    override val id: DidId,

    /**
     * The current state of the DID Document.
     */
    override val type: DidState,

    /**
     * The fetched DID Document.
     */
    val document: DIDDocument
) : DidEvent
