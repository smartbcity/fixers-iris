plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	api(project(":iris-bdd"))

	implementation("org.springframework.vault:spring-vault-core:${Versions.springVault}") // for types
	implementation("city.smartb.i2:i2-spring-boot-starter-auth:${PluginVersions.i2}") // to retrieve jwt in current context

	api("city.smartb.im:im-commons-domain:0.5.0") // for http client

	Dependencies.Jvm.ktor(::implementation)
}
