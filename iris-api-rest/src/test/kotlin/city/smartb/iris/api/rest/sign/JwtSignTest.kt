package city.smartb.iris.api.rest.sign

import city.smartb.iris.api.rest.model.jwt.Jwt
import city.smartb.iris.api.rest.model.jwt.asByte64
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class JwtSignTest {

    @Test
    fun `should build jwt asSHA256ForNoneWithRSA`() {
        val jwt = Jwt.builder()
                .publicKey("publickey")
                .issueTime( LocalDateTime.of(1955, 11, 12, 6, 38, 0))
                .expirationTime( LocalDateTime.of(2015, 10, 21, 7, 28, 0))
                .build()
        System.out.println(jwt.asString())
        val SHASendToPhone = jwt.asSHA256ForNoneWithRSA()
        System.out.println(SHASendToPhone.asByte64())
        val privateKey = KeyPairReader.loadPrivateKey("rsa/adam")
        val sign = SHA256KtTest.signNoneWithRSA(SHASendToPhone, privateKey)

        val signedJWT = jwt.append(sign)
        Assertions.assertThat(signedJWT).isEqualTo("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJwdWJsaWNrZXkiLCJhdWQiOiJodHRwczpcL1wvaXJpcy5zbWFydGIubmV0d29yayIsImlzcyI6ImlyaXMuc21hcnRiLm5ldHdvcmsiLCJleHAiOjE0NDU0MDUyODAsImlhdCI6LTQ0NjE0OTMyMH0.lBcuwpHzQzrXsUG1jTYAMfnxw3LayHCqRtbVSGUVcadNYCXDR8T8Ins6JtSE_HR7GbuPf0vfBna-BPmCbLK1nqgDYUH3Z4pTtLcTzFEjJ9ZUYiqOgp72Vr_M0u9tmDzm8R807obthQte_tzz7sVp1WaQWun4KS5935LCb9CY2Q135R02rOkis81IDoIVOOfA8Iu6No2EG-7XaSTETBI59LYDltNYPW3fmF5S09GxYTxmc1gy2-CA1dgZ9Hdjpr8Qv_vu9iX3Uy1zz4NXMyvlS4jGdjqnHtOBRynk60EvWCsaWgSIeghPmu__D9VM0KU4Fp0K3jHP8Gv941QY1zWi1A")
    }

}