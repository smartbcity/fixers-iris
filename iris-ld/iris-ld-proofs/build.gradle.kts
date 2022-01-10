plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	api(project(":iris-crypto:iris-crypto-rsa"))
	api(project(":iris-ld:iris-ld-jsonld"))
	implementation("com.github.jsonld-java:jsonld-java:${Versions.jsonldJavaVersion}")
//	{
//		changing = true
//	}
	api("com.nimbusds:nimbus-jose-jwt:${Versions.joseJwtVersion}")
}