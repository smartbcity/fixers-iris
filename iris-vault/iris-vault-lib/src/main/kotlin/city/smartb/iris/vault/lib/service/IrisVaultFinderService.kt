package city.smartb.iris.vault.lib.service

import city.smartb.iris.did.DidFeaturesImpl
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
        val publicKey = query.verifiableJsonLd.proof.verificationMethod

        // use did lib to resolve the public key
        // use keypair lib to verify the jsonld

        val isValid = keypairFeatures.verify().invoke(VerifyLibQuery(
            jsonLd = query.verifiableJsonLd,
            publicKey = publicKey
        )).isValid

        return VerifyResult(isValid)
    }


    suspend fun sign(query: SignQuery): SignResult {
        val verifiableJsonLd = keypairFeatures.sign().invoke(SignLibQuery(
            jsonLd = query.jsonLd,
            privateKey = query.privateKeyName,
            method = "transit",
            pathToVerificationKey = "keyIdInDidDoc"
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
}
