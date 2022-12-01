plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	api(project(":iris-bdd"))

	api(project(":iris-vault:iris-vault-domain"))

	implementation("org.springframework.vault:spring-vault-core:${Versions.springVault}") // for types
	implementation("city.smartb.i2:i2-spring-boot-starter-auth:${PluginVersions.i2}") // to retrieve jwt in current context
	api("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")
	api("city.smartb.im:im-commons-domain:0.5.0") // for http client

	Dependencies.Jvm.ktor(::implementation)
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}
