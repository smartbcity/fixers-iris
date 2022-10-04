pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    }
}

rootProject.name = "iris-sdk"


include("iris-crypto:iris-crypto-rsa-key")

include( "iris-ld:iris-ld-jsonld",
"iris-ld:iris-ld-proofs")

include( "iris-jwt")
include( "iris-did")
include( "iris-vc")

include(
    "iris-signer:iris-signer-api",
    "iris-signer:iris-signer-core",
    "iris-signer:iris-signer-client"
)

include ("iris-bdd")

include ("iris-registrar")
