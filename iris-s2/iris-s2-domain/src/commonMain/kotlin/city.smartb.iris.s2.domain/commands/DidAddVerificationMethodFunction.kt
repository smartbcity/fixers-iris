package city.smartb.iris.s2.domain.commands

import city.smartb.iris.s2.domain.DidCommand
import city.smartb.iris.s2.domain.DidEvent
import city.smartb.iris.s2.domain.DidId
import city.smartb.iris.s2.domain.DidState
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias DidAddVerificationMethodCommandFunction = F2Function<DidAddVerificationMethodCommand, DidAddVerificationMethodEvent>

/**
 * Test comment DidAddVerificationMethodCommand
 */
@Serializable
@JsExport
@JsName("DidAddVerificationMethodCommand")
data class DidAddVerificationMethodCommand(
    override val id: DidId,
    val controller: String,
    val keyId: String,
    val publicKey: String,
    val type: String,
    val purpose: List<String>
) : DidCommand

/**
 * Test comment DidAddVerificationMethodEvent
 */
@Serializable
@JsExport
@JsName("DidAddVerificationMethodEvent")
open class DidAddVerificationMethodEvent(
    override val id: DidId,
    override val type: DidState,
    val document: Map<String, @Contextual Any>
) : DidEvent
