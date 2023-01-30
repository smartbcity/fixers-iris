package city.smartb.iris.ld.ldproof.util

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object SHAUtil {
    fun sha256(string: String?): ByteArray {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            digest.digest(string!!.toByteArray(StandardCharsets.UTF_8))
        } catch (ex: NoSuchAlgorithmException) {
            throw RuntimeException(ex.message, ex)
        }
    }
}
