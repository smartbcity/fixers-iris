package city.smartb.iris.keypair.domain

import city.smartb.iris.ld.jsonld.JsonLdObject
import city.smartb.iris.ld.ldproof.VerifiableJsonLd
import f2.dsl.fnc.F2Function

/**
 * Sign a json-LD and attach its proof.
 * It currently supports two ways of signing:
 * - Directly provide a private key in the request and use this key to sign
 * - Specify the name of the transit key to use
 * @d2 function
 * @parent [city.smartb.iris.keypair.domain.D2KeypairPage]
 * @order 30
 */
typealias SignFunction = F2Function<SignQuery, SignResult>

/**
 * @d2 query
 * @parent [SignFunction]
 */
class SignQuery(
    /**
     * The json-LD to sign.
     */
    val jsonLd: JsonLdObject,

    /**
     * Specify the signing method use: "rsa" or "transit"
     * "rsa" uses a provided private key in the privateKey field
     * "transit" uses a transit key which name is provided by the privateKey field
     */
    val method: String,

    /**
     * if method is "rsa", privateKey is privateKey value (only support #PKCS8 format).
     * if method is "transit", privateKey is the name of the key in the vault.
     */
    val privateKey: Any,

    /**
     * The path to the public key to verify the signature.
     */
    val pathToVerificationKey: String
)

/**
 * @d2 result
 * @parent [SignFunction]
 */
class SignResult(
    /**
     * The signed json-LD with its proof.
     */
    val verifiableJsonLd: VerifiableJsonLd
)
