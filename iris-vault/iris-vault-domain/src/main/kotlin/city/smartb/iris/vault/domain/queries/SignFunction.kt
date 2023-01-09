package city.smartb.iris.vault.domain.queries

import city.smartb.iris.jsonld.JsonLdObject
import city.smartb.iris.ldproof.VerifiableJsonLd
import f2.dsl.fnc.F2Function
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias SignLibQuery = city.smartb.iris.keypair.domain.SignQuery
typealias SignFunction = F2Function<SignQuery, SignResult>


@Serializable
class SignQuery(
    @Contextual
    val jsonLd: JsonLdObject,
    val privateKeyName: String
)


@Serializable
open class SignResult(
    @Contextual
    val verifiableJsonLd: VerifiableJsonLd
)
