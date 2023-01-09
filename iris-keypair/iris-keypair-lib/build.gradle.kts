plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    implementation(project(":iris-bdd"))
    api(project(":iris-crypto:iris-crypto-key-rsa"))
    implementation(project(":iris-crypto:iris-crypto-hc-vault-transit"))

    api(project(":iris-keypair:iris-keypair-domain"))

    api(project(":iris-hc-vault:iris-hc-vault-client"))

    api("info.weboftrust:ld-signatures-java:1.0.0")

    implementation("city.smartb.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}")
}
