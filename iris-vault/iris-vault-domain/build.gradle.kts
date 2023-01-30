plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
	kotlin("plugin.serialization")
}

dependencies {
	api("city.smartb.f2:f2-client-ktor:${Versions.f2}")
	implementation(project(":iris-did:iris-did-domain"))
	implementation(project(":iris-keypair:iris-keypair-domain"))
}
