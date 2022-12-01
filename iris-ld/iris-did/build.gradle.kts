plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(":iris-crypto:iris-crypto-key-rsa"))
    api(project(":iris-ld:iris-ld-proofs"))
    api(project(":iris-ld:iris-ld-jsonld"))
}
