pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    }
}

rootProject.name = "iris-sdk"


include(
    "iris-crypto:iris-crypto-key-rsa",
    "iris-crypto:iris-crypto-hc-vault-kv",
    "iris-crypto:iris-crypto-hc-vault-transit",
    "iris-crypto:iris-crypto-hc-vault",
    "iris-crypto:iris-crypto-dsl"
)

include(
    "iris-ld:iris-ld-jsonld",
    "iris-ld:iris-ld-proofs",
    "iris-ld:iris-did",
    "iris-ld:iris-vc"
)

include( "iris-jwt")

include(
    "iris-signer:iris-signer-api",
    "iris-signer:iris-signer-lib",
    "iris-signer:iris-signer-client",
    "iris-signer:iris-signer-domain"
)

include ("iris-bdd")

include ("iris-registrar")

include ("iris-resolver")

include (
    "iris-vault:iris-vault-app",
    "iris-vault:iris-vault-app-domain",
    "iris-vault:iris-vault-client",
    "iris-vault:iris-vault-domain"
)

include (
    "iris-s2:iris-s2-app",
    "iris-s2:iris-s2-domain"
)
