plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("city.smartb.fixers.gradle.publish")

	kotlin("plugin.serialization")
}

kotlin {
	sourceSets {
		commonMain {
			dependencies {
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
