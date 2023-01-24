package city.smartb.iris.vault.lib.service

import city.smartb.iris.crypto.rsa.RSAKeyPairEncoderBase64
import city.smartb.iris.did.DidFeaturesImpl
import city.smartb.iris.did.domain.DidId
import city.smartb.iris.keypair.lib.KeypairFeaturesImpl
import city.smartb.iris.vault.domain.queries.DidGetLibQuery
import city.smartb.iris.vault.domain.queries.DidGetQuery
import city.smartb.iris.vault.domain.queries.DidGetResult
import city.smartb.iris.vault.domain.queries.DidListLibQuery
import city.smartb.iris.vault.domain.queries.DidListQuery
import city.smartb.iris.vault.domain.queries.DidListResult
import city.smartb.iris.vault.domain.queries.SignLibQuery
import city.smartb.iris.vault.domain.queries.SignQuery
import city.smartb.iris.vault.domain.queries.SignResult
import city.smartb.iris.vault.domain.queries.VerifyLibQuery
import city.smartb.iris.vault.domain.queries.VerifyQuery
import city.smartb.iris.vault.domain.queries.VerifyResult
import com.nimbusds.jose.jwk.RSAKey
import f2.dsl.fnc.invoke
import org.springframework.stereotype.Service
import s2.spring.utils.logger.Logger

@Service
class IrisVaultFinderService(
    private val didFeatures: DidFeaturesImpl,
    private val keypairFeatures: KeypairFeaturesImpl
) {

    private val logger by Logger()

    suspend fun verify(query: VerifyQuery): VerifyResult {
        val publicKeyUrl = query.verifiableJsonLd.proof.getVerificationMethod()
            ?: throw Exception("Verification method not found")

        val did = extractDid(publicKeyUrl)
        val didDocument = didFeatures.didGet().invoke(DidGetLibQuery(did)).document
        val verifMethods = didDocument.verificationMethod
        val pubKey = verifMethods.firstOrNull { it.id == publicKeyUrl }
            ?: throw Exception("Public key could not be resolved")

        val parsedPubKey = RSAKey.parse(pubKey.publicKeyJwk as Map<String, Any>).toRSAPublicKey()
        val stringed = RSAKeyPairEncoderBase64.encodePublicKey(parsedPubKey)

        val isValid = keypairFeatures.verify().invoke(VerifyLibQuery(
            jsonLd = query.verifiableJsonLd,
            publicKey = stringed
        )).isValid

        return VerifyResult(isValid)
    }


    suspend fun sign(query: SignQuery): SignResult {
        val verifiableJsonLd = keypairFeatures.sign().invoke(SignLibQuery(
            jsonLd = query.jsonLd,
            privateKey = query.privateKeyName,
            method = "transit",
            pathToVerificationKey = "${query.did}#${query.privateKeyName}"
        )).verifiableJsonLd

        return SignResult(verifiableJsonLd)
    }

    suspend fun didGet(query: DidGetQuery): DidGetResult {
        val query = DidGetLibQuery(query.did)
        return DidGetResult(
            didDocument = didFeatures.didGet().invoke(query).document
        )
    }

    suspend fun didList(query: DidListQuery): DidListResult {
        val query = DidListLibQuery()
        return DidListResult(
            didDocuments = didFeatures.didList().invoke(query).documents
        )
    }

    private fun extractDid(didUrl: String): DidId {
        return didUrl.split("#")[0]
    }
}
