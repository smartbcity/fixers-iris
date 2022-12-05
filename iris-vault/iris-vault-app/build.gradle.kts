plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	api(project(":iris-ld:iris-did"))
	api(project(":iris-bdd"))

	implementation(project(":iris-signer:iris-signer-lib"))

	api(project(":iris-vault:iris-vault-app-domain"))

	api("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}
