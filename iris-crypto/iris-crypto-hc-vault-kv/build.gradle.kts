plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    implementation(project(":iris-crypto:iris-crypto-key-rsa"))
    implementation(project(":iris-crypto:iris-crypto-dsl"))
    api(project(":iris-crypto:iris-crypto-hc-vault"))
}
