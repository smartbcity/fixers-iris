pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    }
}

rootProject.name = "iris-sdk"


include(
    "iris-crypto:iris-crypto-rsa-key",
    "iris-crypto:iris-crypto-hc-vault-kv",
    "iris-crypto:iris-crypto-hc-vault-transit",
    "iris-crypto:iris-crypto-hc-vault",
    "iris-crypto:iris-crypto-dsl"
)

include( "iris-ld:iris-ld-jsonld",
"iris-ld:iris-ld-proofs")

include( "iris-jwt")
include( "iris-did")
include( "iris-vc")

include(
    "iris-signer:iris-signer-api",
    "iris-signer:iris-signer-core",
    "iris-signer:iris-signer-client",
    "iris-signer:iris-signer-domain"
)

include ("iris-bdd")

include (
    "iris-registrar:iris-registrar-app",
    "iris-registrar:iris-registrar-domain"
)
