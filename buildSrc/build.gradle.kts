plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
}

dependencies {
    implementation("city.smartb.fixers.gradle:dependencies:0.3.1")
}