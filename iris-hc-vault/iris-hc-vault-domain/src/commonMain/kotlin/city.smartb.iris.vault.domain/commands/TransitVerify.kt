package city.smartb.iris.vault.domain.commands

class TransitVerifyCommand(
    val keyName: String,
    val input: String, // replace by JsonLd
    val signature: String
)

class TransitVerified(
    val isValid: Boolean
)
