package city.smartb.iris.vault.domain.commands

class TransitSignCommand(
    val keyName: String,
    val input: String // replace by JsonLd
)

class TransitSigned(
    val signature: String
)
