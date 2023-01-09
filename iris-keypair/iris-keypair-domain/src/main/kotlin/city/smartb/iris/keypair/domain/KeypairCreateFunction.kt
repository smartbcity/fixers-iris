package city.smartb.iris.keypair.domain

import f2.dsl.fnc.F2Function

typealias KeypairCreateFunction = F2Function<KeypairCreateCommand, KeypairCreatedEvent>

open class KeypairCreateCommand(
    val keyName: String
)

class KeypairCreatedEvent(
    val id: String,
    val publicKey: String
)
