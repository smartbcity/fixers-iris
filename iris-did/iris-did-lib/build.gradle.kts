plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	implementation(project(":iris-crypto:iris-crypto-key-rsa"))
	implementation(project(":iris-did:iris-did-s2"))

	api(project(":iris-did:iris-did-domain"))

	implementation("city.smartb.ssm:ssm-chaincode-spring-boot-starter:${Versions.ssm}")

	Dependencies.Jvm.f2Function(::implementation)
	Dependencies.Jvm.s2SsmStoring(::implementation)

	implementation("city.smartb.s2:s2-spring-boot-starter-utils-logger:${Versions.s2}")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(project(":iris-bdd"))
}
