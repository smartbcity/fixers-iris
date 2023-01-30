plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    implementation(project(":iris-crypto:iris-crypto-dsl"))
    implementation(project(":iris-hc-vault:iris-hc-vault-client"))

    implementation("city.smartb.i2:i2-spring-boot-starter-auth:${PluginVersions.i2}") // to retrieve jwt in current context
}
