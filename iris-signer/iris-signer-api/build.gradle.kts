plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")

    implementation(project(":iris-signer:iris-signer-lib"))

    implementation(project(":iris-crypto:iris-crypto-key-rsa"))

    implementation(project(":iris-vault:iris-vault-auth-client"))
}
