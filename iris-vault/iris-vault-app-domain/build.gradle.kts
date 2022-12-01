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
