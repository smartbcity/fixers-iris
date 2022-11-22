package city.smartb.iris.s2.domain

import city.smartb.iris.s2.domain.queries.DidGetQueryFunction

interface DidFinder {
    fun getDid(): DidGetQueryFunction
}
