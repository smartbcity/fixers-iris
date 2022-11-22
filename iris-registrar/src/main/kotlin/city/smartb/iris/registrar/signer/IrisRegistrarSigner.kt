package city.smartb.iris.registrar.signer

import city.smartb.iris.crypto.rsa.RSAKeyPairReader
import city.smartb.iris.crypto.rsa.signer.RS256Signer
import city.smartb.iris.did.DIDDocument
import city.smartb.iris.did.DIDDocumentBuilder
import city.smartb.iris.did.DIDSigner
import city.smartb.iris.ldproof.LdProofBuilder
import java.security.interfaces.RSAPrivateKey
import java.util.LinkedHashMap
import java.util.UUID

class IrisRegistrarSigner {

    fun generateKeyId(did: String): String {
        return "$did#key-${UUID.randomUUID()}"
    }

    fun renewRegistrarProof(didDocument: DIDDocument): DIDDocument {
        val vcBuild = DIDDocumentBuilder.fromMap(didDocument.asJson() as LinkedHashMap<String, Any>?)

        val proofBuilder = LdProofBuilder.builder()
            .withChallenge("Challenges")
            .withCreatedNow()
            .withDomain("smartb.city")
            .withProofPurpose("assertionMethod")
            .withVerificationMethod("TODO: urlToPubKey")

        return IrisRegistrarSigner().sign(vcBuild, proofBuilder)
    }

    fun sign(vcBuild: DIDDocumentBuilder, proofBuilder: LdProofBuilder): DIDDocument {
        val vcSign = DIDSigner()
        val pair = RSAKeyPairReader.loadKeyPair("server")
        val signer = RS256Signer((pair.private as RSAPrivateKey))

        return vcSign.sign(vcBuild, proofBuilder, signer)
    }
}
