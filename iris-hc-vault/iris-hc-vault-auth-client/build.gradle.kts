plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	api(project(":iris-hc-vault:iris-hc-vault-domain"))

	implementation("org.springframework.vault:spring-vault-core:${Versions.springVault}") // for types
	implementation("city.smartb.i2:i2-spring-boot-starter-auth:${PluginVersions.i2}") // to retrieve jwt in current context

	Dependencies.Jvm.ktor(::implementation)

	testImplementation(project(":iris-bdd"))
}
