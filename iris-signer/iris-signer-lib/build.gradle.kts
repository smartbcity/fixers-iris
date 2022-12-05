plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    implementation(project(":iris-bdd"))
    implementation(project(":iris-crypto:iris-crypto-key-rsa"))
    implementation(project(":iris-crypto:iris-crypto-hc-vault-transit"))

    api(project(":iris-signer:iris-signer-domain"))

    api(project(":iris-vault:iris-vault-client"))

    api("info.weboftrust:ld-signatures-java:1.0.0")
}
