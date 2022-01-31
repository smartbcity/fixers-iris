plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	api(project(":iris-crypto:iris-crypto-rsa"))
	api(project(":iris-ld:iris-ld-jsonld"))

	implementation("com.github.jsonld-java:jsonld-java:${Versions.jsonldJavaVersion}")
	implementation("com.apicatalog:titanium-json-ld:1.1.0")
	implementation("io.setl:rdf-urdna:1.1")

	implementation("jakarta.json:jakarta.json-api:2.0.1")
	implementation("org.glassfish:jakarta.json:2.0.1")

	api("com.nimbusds:nimbus-jose-jwt:${Versions.joseJwtVersion}")
}
