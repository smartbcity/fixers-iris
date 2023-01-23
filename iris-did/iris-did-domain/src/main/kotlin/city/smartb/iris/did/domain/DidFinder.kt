package city.smartb.iris.did.domain

import city.smartb.iris.did.domain.queries.DidDereferenceFunction
import city.smartb.iris.did.domain.queries.DidGetFunction

interface DidFinder {
    fun getDid(): DidGetFunction

    fun dereference(): DidDereferenceFunction
}
