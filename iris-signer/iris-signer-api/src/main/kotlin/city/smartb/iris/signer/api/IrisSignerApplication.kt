package city.smartb.iris.signer.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["city.smartb.iris"])
open class IrisSignerApplication

fun main(args: Array<String>) {
    runApplication<IrisSignerApplication>(*args)
}
