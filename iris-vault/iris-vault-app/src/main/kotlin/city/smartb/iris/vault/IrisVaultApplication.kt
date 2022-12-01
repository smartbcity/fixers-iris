package city.smartb.iris.vault

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["city.smartb.iris"])
open class IrisVaultApplication

fun main(args: Array<String>) {
    runApplication<IrisVaultApplication>(*args)
}
