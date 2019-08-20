package city.smartb.iris.api.rest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication(scanBasePackageClasses = [Application::class])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}


