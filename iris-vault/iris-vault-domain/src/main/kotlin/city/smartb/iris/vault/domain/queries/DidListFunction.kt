package city.smartb.iris.vault.domain.queries

import city.smartb.iris.did.DIDDocument
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual

typealias DidListFunction = F2Function<DidListQuery, DidListResult>
typealias DidListLibQuery = city.smartb.iris.did.domain.queries.DidListQuery

//@Serializable
class DidListQuery


//@Serializable
open class DidListResult(
    @Contextual
    val didDocuments: List<DIDDocument>
)
