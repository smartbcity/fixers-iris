package city.smartb.iris.did;

import city.smartb.iris.crypto.dsl.signer.Signer;
import city.smartb.iris.ldproof.LdJsonObjectBuilder;
import city.smartb.iris.ldproof.LdProof;
import city.smartb.iris.ldproof.LdProofBuilder;
import city.smartb.iris.ldproof.crypto.RsaSignature2018LdProofSigner;

import java.security.GeneralSecurityException;

public class DIDSigner {

    public DIDDocument sign(DIDDocumentBuilder vcBuild, LdProofBuilder proofBuilder, Signer signer) throws GeneralSecurityException {
        RsaSignature2018LdProofSigner ldSigner = new RsaSignature2018LdProofSigner(signer, proofBuilder);
        LdJsonObjectBuilder builder = LdJsonObjectBuilder.builder(vcBuild.asJson());
        LdProof proof = ldSigner.sign(builder);
        return vcBuild.asJson(proof);
    }

}
