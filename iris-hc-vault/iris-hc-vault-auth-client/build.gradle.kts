plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	implementation("org.springframework.vault:spring-vault-core:${Versions.springVault}") // for types
	implementation("city.smartb.i2:i2-spring-boot-starter-auth:${PluginVersions.i2}") // to retrieve jwt in current context

	Dependencies.ToRemove.imCommons(::implementation)
	Dependencies.Jvm.ktor(::implementation)

	testImplementation(project(":iris-bdd"))
}
