plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")

	kotlin("plugin.serialization")
}

dependencies {
	api("city.smartb.f2:f2-client-ktor:${Versions.f2}")
	api("city.smartb.s2:s2-automate-dsl:${Versions.s2}")
	api(project(":iris-ld:iris-did"))
}
