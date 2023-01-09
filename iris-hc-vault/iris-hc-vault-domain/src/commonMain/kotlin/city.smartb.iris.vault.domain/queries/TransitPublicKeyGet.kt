package city.smartb.iris.vault.domain.queries

class TransitPublicKeyGetQuery(
    val keyName: String
)

class TransitPublicKeyGet(
    val type: String,
    val publicKey: String
)
