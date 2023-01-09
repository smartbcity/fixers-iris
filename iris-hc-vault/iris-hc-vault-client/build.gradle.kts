plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	api(project(":iris-bdd"))

	api(project(":iris-hc-vault:iris-hc-vault-domain"))

	implementation("org.springframework.vault:spring-vault-core:${Versions.springVault}") // for types

	api("city.smartb.im:im-commons-domain:0.5.0") // for http client

	Dependencies.Jvm.ktor(::implementation)
}
