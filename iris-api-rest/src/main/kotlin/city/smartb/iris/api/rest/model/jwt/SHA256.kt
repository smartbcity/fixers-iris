package city.smartb.iris.api.rest.model.jwt

import com.google.common.hash.Hashing
import com.nimbusds.jose.util.Base64URL

fun ByteArray.asSHA256(): ByteArray {
    return  Hashing.sha256().hashBytes(this).asBytes()
}

fun ByteArray.asByte64Url(): String {
    return Base64URL.encode(this).toString()
}