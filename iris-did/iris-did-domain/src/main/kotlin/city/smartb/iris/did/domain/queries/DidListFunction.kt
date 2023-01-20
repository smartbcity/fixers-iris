package city.smartb.iris.did.domain.queries

import city.smartb.iris.ld.did.DIDDocument
import f2.dsl.fnc.F2Function

typealias DidListFunction = F2Function<DidListQuery, DidListEvent>

class DidListQuery

class DidListEvent(
    val documents: List<DIDDocument>
)
