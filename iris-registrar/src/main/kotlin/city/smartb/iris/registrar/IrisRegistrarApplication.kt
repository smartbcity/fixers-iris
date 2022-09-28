package city.smartb.iris.registrar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["city.smartb.iris"])
open class IrisRegistrarApplication

fun main(args: Array<String>) {
    runApplication<IrisRegistrarApplication>(*args)
}
