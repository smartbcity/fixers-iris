package city.smartb.iris.api.rest.sign

import org.bouncycastle.asn1.DERNull
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers
import org.bouncycastle.asn1.x509.AlgorithmIdentifier
import org.bouncycastle.asn1.x509.DigestInfo
import java.security.MessageDigest
import java.util.*

//https://stackoverflow.com/questions/33305800/difference-between-sha256withrsa-and-sha256-then-rsa
fun ByteArray.asSHA256ForNoneWithRSA(): ByteArray {
    val digest = MessageDigest.getInstance("SHA-256").digest(this)
    val sha256Aid = AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE)
    val di = DigestInfo(sha256Aid, digest)
    return di.toASN1Primitive().getEncoded();
}

fun ByteArray.asByte64(): String {
    return Base64.getEncoder().encodeToString(this)
}