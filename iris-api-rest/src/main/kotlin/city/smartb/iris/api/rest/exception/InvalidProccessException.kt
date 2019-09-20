package city.smartb.iris.api.rest.exception

import java.lang.Exception

class InvalidProccessException(override val message: String) : Exception(message) {
}