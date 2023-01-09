package city.smartb.iris.did.domain

import city.smartb.iris.did.domain.queries.DidDereferenceQueryFunction
import city.smartb.iris.did.domain.queries.DidGetQueryFunction

interface DidFinder {
    fun getDid(): DidGetQueryFunction

    fun dereference(): DidDereferenceQueryFunction
}
