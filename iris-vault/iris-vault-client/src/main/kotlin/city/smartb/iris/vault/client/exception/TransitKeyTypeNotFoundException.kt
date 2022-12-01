package city.smartb.iris.vault.client.exception

class TransitKeyTypeNotFoundException(
    val type: String
): Exception("Transit key type [$type] not found")
