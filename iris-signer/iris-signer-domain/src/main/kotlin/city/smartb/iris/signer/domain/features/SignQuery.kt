package city.smartb.iris.signer.domain.features

import city.smartb.iris.jsonld.JsonLdObject
import city.smartb.iris.ldproof.VerifiableJsonLd
import f2.dsl.fnc.F2Function

typealias SignQueryFunction = F2Function<SignQuery, SignQueryResult>

class SignQuery(
//    val type: String, // Specify signing method: from rsa file, kv vault, transit vault
//    val keyName: String, // Specify the name of the key to use
    val jsonLd: JsonLdObject,
    /**
     * if type is rsa, privateKey is privateKey value (only support #PKCS8 format)
     * if transit type, privateKey is the name in transit store
     */
    val privateKey: String,
    val method: String, // currently supports: ["transit", "rsa"]
    val pathToVerificationKey: String
)

class SignQueryResult(
    val verifiableJsonLd: VerifiableJsonLd
)
