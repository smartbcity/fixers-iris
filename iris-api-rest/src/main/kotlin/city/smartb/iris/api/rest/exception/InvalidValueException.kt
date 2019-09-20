package city.smartb.iris.api.rest.exception

import java.lang.Exception


class InvalidValueException(override val message: String) : Exception(message) {
}