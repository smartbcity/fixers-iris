package city.smartb.iris.api.rest.exception

import java.lang.Exception

class InvalidMessageException(override val message: String) : Exception(message) {
}