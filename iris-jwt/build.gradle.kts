plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(":iris-crypto:iris-crypto-key-rsa"))

    api("com.nimbusds:nimbus-jose-jwt:${Versions.joseJwtVersion}")
    api("org.bouncycastle:bcprov-jdk15on:${Versions.bouncycastleVersion}")
}
