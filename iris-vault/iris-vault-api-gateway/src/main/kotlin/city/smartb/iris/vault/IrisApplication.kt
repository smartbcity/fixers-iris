package city.smartb.iris.vault

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["city.smartb.iris"])
open class IrisApplication

fun main(args: Array<String>) {
    runApplication<IrisApplication>(*args)
}
