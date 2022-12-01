package city.smartb.iris.vault.client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["city.smartb.iris"])

open class TestApp

fun main(args: Array<String>) {
    runApplication<TestApp>(*args)
}
