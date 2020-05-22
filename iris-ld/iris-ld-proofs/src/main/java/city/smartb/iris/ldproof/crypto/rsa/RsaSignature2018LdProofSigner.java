package city.smartb.iris.ldproof.crypto.rsa;

import city.smartb.iris.crypto.rsa.signer.Signer;
import city.smartb.iris.ldproof.LdProofBuilder;
import city.smartb.iris.ldproof.signer.LdProofSigner;

public class RsaSignature2018LdProofSigner extends LdProofSigner {

    public final static String JSON_LD_RSA_SIGNATURE_2018 = "RsaSignature2018";

    public RsaSignature2018LdProofSigner(Signer signer, LdProofBuilder builder) {
        super(JSON_LD_RSA_SIGNATURE_2018, signer, builder);
    }


}
