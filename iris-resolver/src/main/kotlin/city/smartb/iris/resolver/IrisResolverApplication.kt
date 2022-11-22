package city.smartb.iris.resolver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["city.smartb.iris"])
open class IrisResolverApplication

fun main(args: Array<String>) {
    runApplication<IrisResolverApplication>(*args)
}
