plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api("com.google.guava:guava:${Versions.guava}")
    api("com.nimbusds:nimbus-jose-jwt:${Versions.joseJwtVersion}")
    api("org.bouncycastle:bcprov-jdk15on:${Versions.bouncycastleVersion}")
}
