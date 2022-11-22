plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	api(project(":iris-did"))
	api(project(":iris-bdd"))

	api(project(":iris-s2:iris-s2-domain"))

	api("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")

	api("city.smartb.ssm:ssm-chaincode-spring-boot-starter:${Versions.ssm}")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}
