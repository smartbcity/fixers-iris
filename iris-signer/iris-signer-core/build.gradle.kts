plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(":iris-vc"))
    implementation(project(":iris-crypto:iris-crypto-rsa-key"))
    implementation(project(":iris-crypto:iris-crypto-hc-vault-kv"))
    implementation(project(":iris-crypto:iris-crypto-hc-vault-transit"))
    implementation(project(":iris-bdd"))

    api(project(":iris-signer:iris-signer-domain"))

    api("info.weboftrust:ld-signatures-java:1.0.0")
}
