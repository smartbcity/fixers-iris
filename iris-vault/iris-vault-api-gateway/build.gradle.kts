plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
	id("org.springframework.boot")
}

dependencies {
	implementation(project(":iris-hc-vault:iris-hc-vault-auth-client"))
	implementation(project(":iris-hc-vault:iris-hc-vault-client"))

	api(project(":iris-vault:iris-vault-lib"))

	api("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")
	implementation("city.smartb.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation("city.smartb.i2:i2-spring-boot-starter-auth:${PluginVersions.i2}") // to retrieve jwt in current context


	testImplementation(project(":iris-bdd"))
}
