plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(":iris-vc"))
    api(project(":iris-crypto:iris-crypto-rsa-key"))
    api(project(":iris-crypto:iris-crypto-hc-vault-kv"))
    api(project(":iris-crypto:iris-crypto-hc-vault-transit"))
    api(project(":iris-bdd"))

    api("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")

    api("info.weboftrust:ld-signatures-java:1.0.0")
}
