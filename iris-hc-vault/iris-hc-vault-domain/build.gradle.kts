plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("city.smartb.fixers.gradle.publish")

	kotlin("plugin.serialization")
}

dependencies {
	Dependencies.Mpp.ktor(::commonMainApi)
	Dependencies.Js.ktor(::jsMainImplementation)
	Dependencies.Jvm.ktor(::jvmMainImplementation)
}
