package city.smartb.iris.vault.domain.commands

class SecretAddCommand(
    val path: String,
    val data: Any
)

class SecretAdded(
    val response: Map<String, Any>
)
