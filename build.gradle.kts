plugins {
    id("city.smartb.fixers.gradle.config") version PluginVersions.fixers
    id("city.smartb.fixers.gradle.sonar") version PluginVersions.fixers
    id("city.smartb.fixers.gradle.d2") version PluginVersions.d2

    id("city.smartb.fixers.gradle.kotlin.mpp") version PluginVersions.fixers apply false
    id("city.smartb.fixers.gradle.kotlin.jvm") version PluginVersions.fixers apply false
    id("city.smartb.fixers.gradle.publish") version PluginVersions.fixers apply false
}

allprojects {
    group = "city.smartb.iris"
    version = System.getenv("VERSION") ?: "experimental-SNAPSHOT"
    repositories {
        mavenCentral()
        maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://repo.danubetech.com/repository/maven-public/") }
    }
}

subprojects {
    plugins.withType(city.smartb.fixers.gradle.config.ConfigPlugin::class.java).whenPluginAdded {
        fixers {
            bundle {
                id = "iris"
                name = "Iris"
                description = "Json-Ld utils"
                url = "https://gitlab.smartb.city/fixers/iris"
            }
        }
    }
}
