plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	api(project(":iris-did"))
	api(project(":iris-bdd"))
	implementation(project(":iris-signer:iris-signer-core"))
	implementation(project(":iris-registrar:iris-registrar-domain"))

	api("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")

	implementation("city.smartb.s2:s2-spring-boot-starter-automate-ssm:${Versions.s2}")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}
