plugins {
    id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api(project(":iris-vc"))
    api(project(":iris-crypto:iris-crypto-rsa"))

    api("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")

    implementation("org.springframework.vault:spring-vault-core:${Versions.springVault}")

//    implementation("io.ktor:ktor-client-core-jvm:${Versions.ktor}")
//    implementation("io.ktor:ktor-client-cio:${Versions.ktor}")
//    implementation("io.ktor:ktor-client-jackson:${Versions.ktor}")
//
//    implementation("io.ktor:ktor-client-content-negotiation:${Versions.ktor}")
//    implementation("io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}")
//    implementation("io.ktor:ktor-serialization-jackson:${Versions.ktor}")
}
