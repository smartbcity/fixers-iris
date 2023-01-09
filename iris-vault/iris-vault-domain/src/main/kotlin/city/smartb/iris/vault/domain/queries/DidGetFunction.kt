package city.smartb.iris.vault.domain.queries

import city.smartb.iris.did.DIDDocument
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias DidGetFunction = F2Function<DidGetQuery, DidGetResult>
typealias DidGetLibQuery = city.smartb.iris.did.domain.queries.DidGetQuery

@Serializable
class DidGetQuery(
    @Contextual
    val did: String
)


@Serializable
open class DidGetResult(
    @Contextual
    val didDocument: DIDDocument
)
