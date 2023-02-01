import city.smartb.gradle.dependencies.FixersDependencies
import city.smartb.gradle.dependencies.FixersPluginVersions
import city.smartb.gradle.dependencies.FixersVersions
import city.smartb.gradle.dependencies.Scope
import city.smartb.gradle.dependencies.add

object PluginVersions {
    val fixers = FixersPluginVersions.fixers
    val d2 = FixersPluginVersions.fixers
    const val kotlin = FixersPluginVersions.kotlin
    const val springBoot = FixersPluginVersions.springBoot
    val i2 = fixers
}

object Versions {
    val s2 = PluginVersions.fixers
    val f2 = PluginVersions.fixers
    val ssm = PluginVersions.fixers

    const val guava = "31.0.1-jre"
    const val jacksonVersion = "2.13.0"
    const val bouncycastleVersion = "1.70"

    const val joseJwtVersion = "9.15.2"

    const val springVault = "2.3.1"
    const val junit = "5.9.1"
    const val ktor = "2.0.3"
}

object Dependencies {
    object ToRemove {
        fun imCommons(scope: Scope) = scope.add(
            "city.smartb.im:im-commons-domain:0.5.0"
        )
    }
    object Jvm {
        fun ktor(scope: Scope) = scope.add(
            "io.ktor:ktor-client-core:${Versions.ktor}",
            "io.ktor:ktor-client-content-negotiation:${Versions.ktor}",
            "io.ktor:ktor-client-cio:${Versions.ktor}",
            "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}",
            "io.ktor:ktor-serialization-jackson:${Versions.ktor}"
        )

        fun s2SsmStoring(scope: Scope) = scope.add(
            "city.smartb.s2:s2-spring-boot-starter-storing-ssm:${Versions.s2}"
        )
        fun f2Function(scope: Scope) = scope.add(
            "city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}"
        )
    }

    object Js {
        fun ktor(scope: Scope) = scope.add(
            "io.ktor:ktor-client-core-js:${Versions.ktor}",
            "io.ktor:ktor-client-json-js:${Versions.ktor}"
        )
    }

    object Mpp {
        fun ktor(scope: Scope) = scope.add(
            "io.ktor:ktor-client-core:${Versions.ktor}",
            "io.ktor:ktor-client-serialization:${Versions.ktor}"
        )
    }
}
