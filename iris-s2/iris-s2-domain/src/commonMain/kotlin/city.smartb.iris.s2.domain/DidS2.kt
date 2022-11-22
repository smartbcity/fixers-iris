package city.smartb.iris.s2.domain

import city.smartb.iris.s2.domain.commands.DidAddVerificationMethodCommand
import city.smartb.iris.s2.domain.commands.DidCreateCommand
import city.smartb.iris.s2.domain.commands.DidRevokeCommand
import city.smartb.iris.s2.domain.commands.DidRevokeVerificationMethodCommand
import city.smartb.iris.s2.domain.commands.DidUpdateCommand
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlinx.serialization.Serializable
import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2Event
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2Role
import s2.dsl.automate.S2State
import s2.dsl.automate.builder.s2

typealias DidId = String

@JsExport
@JsName("didS2")
fun didS2(): S2Automate {
	return s2 {
		name = "DidS2"
		init<DidCreateCommand> {
			to = DidState.Created()
			role = DidRole.Admin()
		}
		transaction<DidAddVerificationMethodCommand> {
			from = DidState.Created()
			to = DidState.Created()
			role = DidRole.Admin()
		}
		transaction<DidRevokeVerificationMethodCommand> {
			from = DidState.Created()
			to = DidState.Created()
			role = DidRole.Admin()
		}
		transaction<DidRevokeCommand> {
			from = DidState.Created()
			to = DidState.Revoked()
			role = DidRole.Admin()
		}
		transaction<DidUpdateCommand> {
			from = DidState.Created()
			to = DidState.Created()
			role = DidRole.Admin()
		}
	}
}

@Serializable
@JsExport
@JsName("DidRole")
sealed class DidRole : S2Role {
	class Admin : DidRole()
	class Owner : DidRole()

	override fun toString(): String {
		return this::class.simpleName!!
	}
}

@Serializable
@JsExport
@JsName("DidState")
open class DidState(
	override val position: Int,
) : S2State {
	@Serializable
	open class Created : DidState(0)

	@Serializable
	open class Actived : DidState(1)

	@Serializable
	open class Revoked : DidState(2)

	override fun toString(): String {
		return this::class.simpleName!!
	}
}

// @JsExport
@JsName("DidInitCommand")
interface DidInitCommand : S2InitCommand

// @JsExport
@JsName("DidCommand")
interface DidCommand : S2Command<DidId>

// @JsExport
@JsName("DidEvent")
interface DidEvent : S2Event<DidState, DidId>
