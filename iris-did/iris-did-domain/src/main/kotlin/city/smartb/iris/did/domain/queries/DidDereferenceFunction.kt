package city.smartb.iris.did.domain.queries

import city.smartb.iris.did.domain.DidEvent
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.did.domain.DidState
import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.F2Function

/**
 * Resolve a resource inside a DID Document.
 * NOT TESTED YET
 * @d2 function
 * @parent [city.smartb.iris.did.domain.D2DidPage]
 * @order 70
 */
typealias DidDereferenceFunction = F2Function<DidDereferenceQuery, DidDereferenceEvent>

interface DidDereferenceQueryPayload {
	val id: DidId
}

/**
 * @d2 query
 * @parent [DidDereferenceFunction]
 */
class DidDereferenceQuery(
    /**
     * The DID Url of the resource to dereference.
     */
    override val id: DidId
) : DidDereferenceQueryPayload

/**
 * @d2 result
 * @parent [DidDereferenceFunction]
 */
class DidDereferenceEvent(
    /**
     * The DID of the resource to dereference.
     */
    override val id: DidId,

    /**
     * The current state of the DID.
     */
    override val type: DidState,

    /**
     * The dereferenced resource.
     */
    val document: DIDDocument
) : DidEvent
