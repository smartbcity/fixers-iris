plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	implementation(project(":iris-crypto:iris-crypto-key-rsa"))
	implementation(project(":iris-did:iris-did-s2"))

	api(project(":iris-did:iris-did-domain"))

	api(project(":iris-bdd"))

	api("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")

	implementation("city.smartb.ssm:ssm-chaincode-spring-boot-starter:${Versions.ssm}")
	implementation("city.smartb.s2:s2-spring-boot-starter-automate-ssm:${Versions.s2}")

	implementation("city.smartb.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}
