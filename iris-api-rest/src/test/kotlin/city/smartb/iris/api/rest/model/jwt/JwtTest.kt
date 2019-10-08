package city.smartb.iris.api.rest.model.jwt

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

class JwtTest {

    @Test
    fun `should build jwt`() {
        val jwt = Jwt.builder()
                .publicKey("publickey")
                .issueTime( LocalDateTime.of(1955, 11, 12, 6, 38, 0))
                .expirationTime( LocalDateTime.of(2015, 10, 21, 7, 28, 0))
                .build().asByteArray()

        Assertions.assertThat(String(jwt)).isEqualTo("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJwdWJsaWNrZXkiLCJhdWQiOiJodHRwczpcL1wvaXJpcy5zbWFydGIubmV0d29yayIsImlzcyI6ImlyaXMuc21hcnRiLm5ldHdvcmsiLCJleHAiOjE0NDU0MDUyODAsImlhdCI6LTQ0NjE0OTMyMH0")
    }

    @Test
    fun `should build jwt asSHA256ForNoneWithRSA`() {
        val jwt = Jwt.builder()
                .publicKey("publickey")
                .issueTime( LocalDateTime.of(1955, 11, 12, 6, 38, 0))
                .expirationTime( LocalDateTime.of(2015, 10, 21, 7, 28, 0))
                .build().asSHA256ForNoneWithRSA()

        Assertions.assertThat(Base64.getEncoder().encodeToString(jwt)).isEqualTo("MDEwDQYJYIZIAWUDBAIBBQAEIEKMpxmGiBTmNXX/BOc43yNF6Mozpl7f3uSFOpTKa4t8")
    }

}