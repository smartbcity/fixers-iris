package city.smartb.iris.api.rest.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class InvalidValueException(override val message: String) : Exception(message) {
}