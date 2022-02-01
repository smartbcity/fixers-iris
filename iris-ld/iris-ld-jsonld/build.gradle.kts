plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	api("com.fasterxml.jackson.core:jackson-core:${Versions.jacksonVersion}")
	api("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:${Versions.jacksonVersion}")
	api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jacksonVersion}")
}