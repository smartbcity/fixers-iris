package city.smartb.iris.registrar.domain.features

import f2.dsl.fnc.F2Function
import city.smartb.iris.registrar.domain.DidCommand
import city.smartb.iris.registrar.domain.DidEvent
import city.smartb.iris.registrar.domain.DidId
import city.smartb.iris.registrar.domain.DidState
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable

typealias DidAddPublicKeyCommandFunction = F2Function<DidAddPublicKeyCommand, DidAddPublicKeyEvent>

/**
 * Test comment DidAddPublicKeyCommandPayload
 */
@JsExport
@JsName("DidAddPublicKeyCommandPayload")
class DidAddPublicKeyCommandPayload(
    val id: DidId,
)

/**
 * Test comment DidAddPublicKeyCommand
 */
@Serializable
@JsExport
@JsName("DidAddPublicKeyCommand")
data class DidAddPublicKeyCommand(
    override val id: DidId,
    val publicKey: String?,
    val type: String
) : DidCommand

/**
 * Test comment DidAddPublicKeyEvent
 */
@Serializable
@JsExport
@JsName("DidAddPublicKeyEvent")
open class DidAddPublicKeyEvent(
    override val id: DidId,
    override val type: DidState,
) : DidEvent
