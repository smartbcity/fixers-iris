package city.smartb.iris.did.domain

import city.smartb.iris.did.domain.commands.DidCreateCommand
import city.smartb.iris.did.domain.commands.DidProofUpdateCommand
import city.smartb.iris.did.domain.commands.DidRevokeCommand
import city.smartb.iris.did.domain.commands.DidRevokeVerificationMethodCommand
import city.smartb.iris.did.domain.commands.DidUpdateCommand
import city.smartb.iris.did.domain.commands.DidVerificationMethodAddCommand
import kotlinx.serialization.Serializable
import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2Event
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2Role
import s2.dsl.automate.S2State
import s2.dsl.automate.builder.s2

typealias DidId = String

fun didS2(): S2Automate {
	return s2 {
		name = "DidS2"
		init<DidCreateCommand> {
			to = DidState.Created()
			role = DidRole.Admin()
		}
		transaction<DidVerificationMethodAddCommand> {
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
		transaction<DidProofUpdateCommand> {
			from = DidState.Created()
			to = DidState.Created()
			role = DidRole.Admin()
		}
	}
}

@Serializable
sealed class DidRole : S2Role {
	class Admin : DidRole()
	class Owner : DidRole()

	override fun toString(): String {
		return this::class.simpleName!!
	}
}

@Serializable
open class DidState(
	override val position: Int,
) : S2State {
	@Serializable
	open class Created : DidState(0)

	@Serializable
	open class Actived : DidState(1)

	@Serializable
	open class Signed : DidState(2)

	@Serializable
	open class Revoked : DidState(3)

	override fun toString(): String {
		return this::class.simpleName!!
	}
}

interface DidInitCommand : S2InitCommand

interface DidCommand : S2Command<DidId>

interface DidEvent : S2Event<DidState, DidId>
