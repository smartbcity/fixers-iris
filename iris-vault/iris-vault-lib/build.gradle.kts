plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	implementation(project(":iris-did:iris-did-lib"))
	implementation(project(":iris-keypair:iris-keypair-lib"))

	api(project(":iris-vault:iris-vault-domain"))

	implementation("city.smartb.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}")
	api("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(project(":iris-bdd"))
}
