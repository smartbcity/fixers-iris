package city.smartb.iris.signer.domain.features

import f2.dsl.fnc.F2Function

typealias CreateKeyCommandFunction = F2Function<CreateKeyCommand, CreateKeyCommandResult>

class CreateKeyCommand(
    val keyName: String,
    val type: String
)

class CreateKeyCommandResult
