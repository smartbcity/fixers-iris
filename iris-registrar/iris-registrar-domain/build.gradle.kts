plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("city.smartb.fixers.gradle.publish")

	kotlin("plugin.serialization")
}

kotlin {
	sourceSets {
		commonMain {
			dependencies {
				api("city.smartb.f2:f2-client-ktor:${Versions.f2}")
				implementation("city.smartb.s2:s2-automate-dsl:0.10.0")
			}
		}
		jsMain {
			dependencies {
			}
		}
		jvmMain {
			dependencies {
			}
		}
	}
}
