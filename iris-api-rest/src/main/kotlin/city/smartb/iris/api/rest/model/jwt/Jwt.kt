package city.smartb.iris.api.rest.model.jwt

import com.nimbusds.jose.*
import com.nimbusds.jwt.JWTClaimsSet
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

class Jwt(
        private val jwsObject: JWSObject
) {

    data class Builder(
            var publicKey: String? = null,
            var issueTime: LocalDateTime? = null,
            var expirationTime: LocalDateTime? = null,
            var uuid: String? = null
    ) {
        fun publicKey(publicKey: String) = apply { this.publicKey = publicKey }

        fun uuid(uuid: String) = apply { this.uuid = uuid }

        fun issueTime(issueTime: LocalDateTime) = apply { this.issueTime = issueTime }

        fun expirationTime(expirationTime: LocalDateTime) = apply { this.expirationTime = expirationTime }

        fun fromNow() = apply { this.issueTime = LocalDateTime.now() }

        fun valid(newValue: Long, unit: ChronoUnit) = apply { this.expirationTime = LocalDateTime.now().plus(newValue, unit) }

        fun build(): Jwt {
            val jwtClaims = JWTClaimsSet.Builder()
                    .issuer("iris.smartb.network")
                    .subject(publicKey)
                    .audience(Arrays.asList("https://iris.smartb.network"))
                    .expirationTime(expirationTime!!.toJavaDate())
                    .issueTime(issueTime!!.toJavaDate())
                    .jwtID(uuid)
                    .build()

            val jwsObject = JWSObject(
                    JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
                    Payload(jwtClaims.toJSONObject()))
            return Jwt(jwsObject)
        }

        fun LocalDateTime.toJavaDate(): Date {
            return Date
                    .from(this.atZone(ZoneId.systemDefault())
                            .toInstant())
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    fun asByteArray(): ByteArray {
        return jwsObject.signingInput
    }

    fun asString(): String {
        return String(jwsObject.signingInput)
    }

    fun asSHA256ForNoneWithRSA(): ByteArray {
        return jwsObject.signingInput.asSHA256ForNoneWithRSA()
    }

    fun append(signature: String) :String {
        return String(jwsObject.signingInput) + '.' + signature
    }

}