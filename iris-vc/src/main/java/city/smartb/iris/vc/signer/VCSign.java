package city.smartb.iris.vc.signer;

import city.smartb.iris.crypto.rsa.signer.Signer;
import city.smartb.iris.ldproof.LdJsonObjectBuilder;
import city.smartb.iris.ldproof.LdProof;
import city.smartb.iris.ldproof.LdProofBuilder;
import city.smartb.iris.ldproof.crypto.RsaSignature2018LdProofSigner;
import city.smartb.iris.vc.VerifiableCredential;
import city.smartb.iris.vc.VerifiableCredentialBuilder;

import java.security.GeneralSecurityException;

public class VCSign {

    public VerifiableCredential sign(VerifiableCredentialBuilder vcBuild, LdProofBuilder proofBuilder, Signer signer) throws GeneralSecurityException {
        RsaSignature2018LdProofSigner ldSigner = new RsaSignature2018LdProofSigner(signer, proofBuilder);
        LdJsonObjectBuilder builder = LdJsonObjectBuilder.builder(vcBuild.asJson());
        LdProof proof = ldSigner.sign(builder);
        return vcBuild.build(proof);
    }
}
