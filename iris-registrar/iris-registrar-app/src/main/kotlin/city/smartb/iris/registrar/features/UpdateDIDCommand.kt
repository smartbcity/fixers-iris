package city.smartb.iris.registrar.features

import f2.dsl.fnc.F2Function
import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

typealias UpdateDIDCommandFunction = F2Function<UpdateDIDCommand, UpdateDIDCommandResult>

class UpdateDIDCommand(
)

class UpdateDIDCommandResult(
)

@Configuration
open class UpdateDIDCommandFunctionImpl {
    @Bean
    open fun updateDIDCommandFunction(): UpdateDIDCommandFunction = f2Function { query ->
        UpdateDIDCommandResult()
    }
}
