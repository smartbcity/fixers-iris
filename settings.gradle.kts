pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    }
}

rootProject.name = "iris-sdk"


include(
    "iris-crypto:iris-crypto-key-rsa",
    "iris-crypto:iris-crypto-hc-vault-transit",
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
    "iris-keypair:iris-keypair-lib",
    "iris-keypair:iris-keypair-domain"
)

include ("iris-bdd")

include ("iris-registrar")

include ("iris-resolver")

include (
    "iris-vault:iris-vault-api-gateway",
    "iris-vault:iris-vault-lib",
    "iris-vault:iris-vault-domain"
)

include (
    "iris-hc-vault:iris-hc-vault-client",
    "iris-hc-vault:iris-hc-vault-auth-client",
    "iris-hc-vault:iris-hc-vault-domain"
)

include (
    "iris-did:iris-did-s2",
    "iris-did:iris-did-lib",
    "iris-did:iris-did-domain"
)
