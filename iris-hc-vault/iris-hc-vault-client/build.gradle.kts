plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	api(project(":iris-hc-vault:iris-hc-vault-domain"))

	implementation("org.springframework.vault:spring-vault-core:${Versions.springVault}") // for types

	Dependencies.ToRemove.imCommons(::api)

	Dependencies.Jvm.ktor(::implementation)
	testImplementation(project(":iris-bdd"))
}
