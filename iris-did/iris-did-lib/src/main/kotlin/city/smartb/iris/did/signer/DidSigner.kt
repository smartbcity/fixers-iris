//package city.smartb.iris.did.signer
//
//import city.smartb.iris.crypto.rsa.RSAKeyPairReader
//import city.smartb.iris.did.DIDDocument
//import city.smartb.iris.signer.domain.features.SignQuery
//import city.smartb.iris.signer.domain.features.SignQueryFunction
//import f2.dsl.fnc.invoke
//import org.springframework.stereotype.Service
//
//@Service
//class DidSigner(
//    private val signQueryFunction: SignQueryFunction
//) {
//
//    suspend fun sign(didDocument: DIDDocument): DIDDocument {
//        val pair = RSAKeyPairReader.loadKeyPair("server")
//
//        val signedDidDocument = signQueryFunction.invoke(SignQuery(
//            jsonLd = didDocument,
//            privateKey = pair.private.toString(),
//            method = "rsa",
//            pathToVerificationKey = "todo path"
//        )).verifiableJsonLd
//
//        return DIDDocument(signedDidDocument.asJson())
//    }
//
//}
