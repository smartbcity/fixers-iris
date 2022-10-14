package city.smartb.iris.registrar.model

class DIDCreateOptions(
    val network: String?,
    val storeSecrets: Boolean = true,
    val returnSecrets: Boolean = false
)
