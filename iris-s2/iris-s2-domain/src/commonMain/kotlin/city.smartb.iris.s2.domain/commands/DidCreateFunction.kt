package city.smartb.iris.s2.domain.commands

import city.smartb.iris.s2.domain.DidEvent
import city.smartb.iris.s2.domain.DidInitCommand
import city.smartb.iris.s2.domain.DidState
import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias DidCreateCommandFunction = F2Function<DidCreateCommand, DidCreatedEvent>

@Serializable
@JsExport
@JsName("DidCreateCommand")
open class DidCreateCommand: DidInitCommand

@Serializable
@JsExport
@JsName("DidCreatedEvent")
class DidCreatedEvent(
    override val id: String,
    override val type: DidState,
    val document: Map<String, @Contextual Any>
) : DidEvent
