package city.smartb.iris.vault.domain.queries

import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias PublicKeyGetQueryFunction = F2Function<PublicKeyGetQuery, PublicKeyGetEvent>

@Serializable
@JsExport
@JsName("PublicKeyGetQuery")
class PublicKeyGetQuery(
    val name: String,
    val format: String = "jwk" // jwk, base64
)

@Serializable
@JsExport
@JsName("PublicKeyGetEvent")
class PublicKeyGetEvent(
    val publicKey: Map<String, @Contextual Any>
)
