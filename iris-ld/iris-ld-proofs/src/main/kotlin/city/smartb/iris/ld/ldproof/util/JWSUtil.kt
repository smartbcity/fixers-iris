package city.smartb.iris.ld.ldproof.util

import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.util.Base64URL
import java.nio.charset.StandardCharsets

object JWSUtil {
    fun getJwsSigningInput(header: JWSHeader, signingInput: ByteArray): ByteArray {
        val encodedHeader = encodeHeader(header)
        val jwsSigningInput = ByteArray(encodedHeader.size + 1 + signingInput.size)
        System.arraycopy(encodedHeader, 0, jwsSigningInput, 0, encodedHeader.size)
        jwsSigningInput[encodedHeader.size] = '.'.code.toByte()
        System.arraycopy(signingInput, 0, jwsSigningInput, encodedHeader.size + 1, signingInput.size)
        return jwsSigningInput
    }

    private fun encodeHeader(header: JWSHeader): ByteArray {
        return if (header.parsedBase64URL != null) {
            header.parsedBase64URL.toString().toByteArray(StandardCharsets.UTF_8)
        } else {
            header.toBase64URL().toString().toByteArray(StandardCharsets.UTF_8)
        }
    }

    fun serializeDetachedJws(jwsHeader: JWSHeader, signature: Base64URL): String {
        return jwsHeader.toBase64URL().toString() + '.' + '.' + signature.toString()
    }
}
