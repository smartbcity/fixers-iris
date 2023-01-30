package city.smartb.iris.bdd

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@EntityScan("city.smartb.iris")
@SpringBootApplication(scanBasePackages = ["city.smartb.iris"])
open class TestApplication

fun main(args: Array<String>) {
	runApplication<TestApplication>(*args)
}
