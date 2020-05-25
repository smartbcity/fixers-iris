package city.smartb.iris.vc.signer;

import city.smartb.iris.crypto.rsa.verifier.Verifier;
import city.smartb.iris.ldproof.crypto.RsaSignature2018LdProofVerifier;
import city.smartb.iris.vc.VerifiableCredential;

import java.security.GeneralSecurityException;

public class VCVerifier {

    public Boolean verify(VerifiableCredential vc, Verifier verifier) throws GeneralSecurityException {
        RsaSignature2018LdProofVerifier ldSigner = new RsaSignature2018LdProofVerifier(verifier);
        return ldSigner.verify(vc.toJSON());
    }
}
