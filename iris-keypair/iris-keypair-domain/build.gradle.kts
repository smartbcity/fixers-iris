plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(":iris-ld:iris-ld-proofs"))
    api("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")
}
