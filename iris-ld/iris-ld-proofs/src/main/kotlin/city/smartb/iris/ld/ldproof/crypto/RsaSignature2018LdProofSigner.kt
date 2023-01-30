package city.smartb.iris.ld.ldproof.crypto

import city.smartb.iris.crypto.dsl.signer.Signer
import city.smartb.iris.ld.ldproof.LdProofBuilder
import city.smartb.iris.ld.ldproof.signer.LdProofSigner

class RsaSignature2018LdProofSigner(signer: Signer, builder: LdProofBuilder) : LdProofSigner(
    JSON_LD_RSA_SIGNATURE_2018, signer, builder
) {
    companion object {
        const val JSON_LD_RSA_SIGNATURE_2018 = "RsaSignature2018"
    }
}
