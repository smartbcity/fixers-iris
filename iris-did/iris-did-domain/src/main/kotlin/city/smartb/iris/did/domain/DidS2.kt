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
	companion object {
		const val DID_STATE_CREATED_POSITION = 0
		const val DID_STATE_ACTIVED_POSITION = 1
		const val DID_STATE_SIGNED_POSITION = 2
		const val DID_STATE_REVOKED_POSITION = 3
	}

	@Serializable
	open class Created : DidState(DID_STATE_CREATED_POSITION)

	@Serializable
	open class Actived : DidState(DID_STATE_ACTIVED_POSITION)

	@Serializable
	open class Signed : DidState(DID_STATE_SIGNED_POSITION)

	@Serializable
	open class Revoked : DidState(DID_STATE_REVOKED_POSITION)

	override fun toString(): String {
		return this::class.simpleName!!
	}
}

interface DidInitCommand : S2InitCommand

interface DidCommand : S2Command<DidId>

interface DidEvent : S2Event<DidState, DidId>
