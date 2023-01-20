package city.smartb.iris.ld.did

import city.smartb.iris.ld.jsonld.JsonLdObject
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import java.security.PublicKey
import java.security.interfaces.RSAPublicKey
import java.util.UUID

class DIDVerificationMethodBuilder(json: Map<String, Any>, private var publicKey: PublicKey?) :
    JsonLdObject(json) {

    companion object {
        fun create(): DIDVerificationMethodBuilder {
            return DIDVerificationMethodBuilder(LinkedHashMap(), null)
        }
    }

    fun withId(id: String): DIDVerificationMethodBuilder {
        jsonLdObject[JSON_LD_ID] = id
        return this
    }

    fun withController(controller: String): DIDVerificationMethodBuilder {
        jsonLdObject[ControledJsonLdObject.JSON_LD_CONTROLER] = controller
        return this
    }

    fun withType(type: String): DIDVerificationMethodBuilder {
        jsonLdObject[JSON_LD_TYPE] = type
        return this
    }

    fun withPublicKey(key: PublicKey): DIDVerificationMethodBuilder {
        publicKey = key
        return this
    }

    fun build(): DIDVerificationMethod {
        addPublicKeyProperties()
        return DIDVerificationMethod(jsonLdObject)
    }

    private fun addPublicKeyProperties(): DIDVerificationMethodBuilder {
        if (publicKey == null) return this
        if (DIDVerificationMethod.RSA_VERIFICATION_2018 == this.type) {
            val jwk: JWK = RSAKey.Builder(publicKey as RSAPublicKey?)
                .keyUse(KeyUse.SIGNATURE)
                .keyID(UUID.randomUUID().toString())
                .build()
            jsonLdObject[DIDVerificationMethod.JSON_LD_PUBLICKEYJWK] = jwk.toPublicJWK().toJSONObject()
            return this
        }
        return this
    }
}
