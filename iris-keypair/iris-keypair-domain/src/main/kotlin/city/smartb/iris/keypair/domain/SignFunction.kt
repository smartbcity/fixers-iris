package city.smartb.iris.keypair.domain

import city.smartb.iris.ld.jsonld.JsonLdObject
import city.smartb.iris.ld.ldproof.VerifiableJsonLd
import f2.dsl.fnc.F2Function

typealias SignFunction = F2Function<SignQuery, SignResult>

class SignQuery(
    val jsonLd: JsonLdObject,
    /**
     * if type is rsa, privateKey is privateKey value (only support #PKCS8 format)
     * if transit type, privateKey is the name in transit store
     */
    val privateKey: String,
    val method: String, // currently supports: ["transit", "rsa"]
    val pathToVerificationKey: String
)

class SignResult(
    val verifiableJsonLd: VerifiableJsonLd
)
