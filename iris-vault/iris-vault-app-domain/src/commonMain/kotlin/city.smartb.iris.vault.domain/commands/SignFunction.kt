package city.smartb.iris.vault.domain.commands

import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias SignCommandFunction = F2Function<SignCommand, SignedEvent>

/**
 * Test comment SignCommand
 */
@Serializable
@JsExport
@JsName("SignCommand")
class SignCommand(
    val keyName: String,
    val payload: Map<String, @Contextual Any>
)

/**
 * Test comment SignedEvent
 */
@Serializable
@JsExport
@JsName("SignedEvent")
open class SignedEvent(
    val payloadWithSignature: Map<String, @Contextual Any>
)
