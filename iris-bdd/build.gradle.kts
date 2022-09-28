plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
    id("city.smartb.fixers.gradle.publish")
}

dependencies {
    api ("org.junit.jupiter:junit-jupiter-api:${Versions.junit}")

    api("org.springframework.boot:spring-boot-starter-test:${PluginVersions.springBoot}") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(group = "com.vaadin.external.google", module = "android-json")
    }
}
