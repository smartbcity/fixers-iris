package city.smartb.iris.registrar.features

import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

typealias DeactivateDIDCommandFunction = F2Function<DeactivateDIDCommand, DeactivateDIDCommandResult>

class DeactivateDIDCommand(
)

class DeactivateDIDCommandResult(
)

@Configuration
open class DeactivateDIDCommandFunctionImpl {
    @Bean
    open fun deactivateDIDCommandFunction(): DeactivateDIDCommandFunction = f2Function { query ->
        DeactivateDIDCommandResult()
    }
}
