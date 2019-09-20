package city.smartb.iris.api.rest.exception

import java.lang.Exception

class InvalidResponseException(override val message: String) : Exception(message) {
}