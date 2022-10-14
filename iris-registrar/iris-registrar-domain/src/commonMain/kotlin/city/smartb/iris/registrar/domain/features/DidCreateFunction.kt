package city.smartb.iris.registrar.domain.features

import f2.dsl.fnc.F2Function
import city.smartb.iris.registrar.domain.DidEvent
import city.smartb.iris.registrar.domain.DidId
import city.smartb.iris.registrar.domain.DidInitCommand
import city.smartb.iris.registrar.domain.DidState
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName

typealias DidCreateCommandFunction = F2Function<DidCreateCommand, DidCreatedEvent>

@Serializable
@JsExport
@JsName("DidCreateCommand")
open class DidCreateCommand(
//    val id: DidId,
) : DidInitCommand

@Serializable
@JsExport
@JsName("DidCreatedEvent")
class DidCreatedEvent(
    override val id: String,
    override val type: DidState,
) : DidEvent
