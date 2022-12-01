package city.smartb.iris.vault.domain.commands

import f2.dsl.fnc.F2Function
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias PrivateKeyGenerateCommandFunction = F2Function<PrivateKeyGenerateCommand, PrivateKeyGeneratedEvent>


/**
 * Test comment PrivateKeyGenerateCommand
 */
@Serializable
@JsExport
@JsName("PrivateKeyGenerateCommand")
class PrivateKeyGenerateCommand(
    val name: String
)

/**
 * Test comment PrivateKeyGeneratedEvent
 */
@Serializable
@JsExport
@JsName("PrivateKeyGeneratedEvent")
open class PrivateKeyGeneratedEvent(
    val publicKey: Map<String, @Contextual Any>
)
