plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")

	kotlin("plugin.serialization")
}

dependencies {
	implementation(project(":iris-did:iris-did-domain"))
	Dependencies.Jvm.f2Function(::implementation)
	Dependencies.Jvm.s2SsmStoring(::implementation)

	testImplementation(project(":iris-bdd"))
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}
