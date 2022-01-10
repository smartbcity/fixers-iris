plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	implementation("com.github.jsonld-java:jsonld-java:${Versions.jsonldJavaVersion}")
//	{
//		changing = true
//	}
	api("com.fasterxml.jackson.core:jackson-core:${Versions.jacksonVersion}")
	api("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:${Versions.jacksonVersion}")
	api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jacksonVersion}")
}