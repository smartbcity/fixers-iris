package city.smartb.iris.signer.api.service

import city.smartb.iris.bdd.TestBase
import city.smartb.iris.signer.core.features.key.VaultCreateKeyCommand
import city.smartb.iris.signer.core.features.key.VaultGetKeyQuery
import city.smartb.iris.signer.core.features.vc.VaultCreateVerifiableCredentialQuery
import city.smartb.iris.signer.core.features.vc.VaultVerifyVerifiableCredentialQuery
import city.smartb.iris.signer.core.utils.getPrivateKey
import city.smartb.iris.signer.core.utils.getPublicKey
import city.smartb.iris.vc.VerifiableCredential
import f2.dsl.fnc.invoke
import java.util.LinkedHashMap
import java.util.UUID
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class IrisSignerServiceTest: TestBase() {

    @Autowired
    private lateinit var irisSignerService: IrisSignerService

    @Test
    fun createAndGetKey(): Unit = runBlocking {
        val keyName = "testKey-${UUID.randomUUID().toString()}"
        irisSignerService.createKey().invoke(VaultCreateKeyCommand(keyName))

        val keyPair = irisSignerService.getKey().invoke(VaultGetKeyQuery(keyName)).vaultRawResponse!!

        val priv = keyPair.getPrivateKey()
        val pub = keyPair.getPublicKey()

        Assertions.assertThat(priv).isNotNull
        Assertions.assertThat(pub).isNotNull

        Assertions.assertThat(priv).isNotEmpty
        Assertions.assertThat(pub).isNotEmpty
    }

    @Test
    fun createAndVerifyVC(): Unit = runBlocking {
        val keyName = "testKey-${UUID.randomUUID().toString()}"
        irisSignerService.createKey().invoke(VaultCreateKeyCommand(keyName))

        val payload = mapOf("key1" to "val1", "key2" to "val2")

        val id = "id-${UUID.randomUUID().toString()}"
        val issuer = "issuer-${UUID.randomUUID().toString()}"

        val vcResponse = irisSignerService.signPayload().invoke(
            VaultCreateVerifiableCredentialQuery(
                keyName = keyName,
                issuer = issuer,
                subject = payload,
                id = id
            )).vc as LinkedHashMap<String, Any>

        val vc = VerifiableCredential.from(vcResponse)

        Assertions.assertThat(vc.id).isEqualTo(id)
        Assertions.assertThat(vc.issuer).isEqualTo(issuer)
        Assertions.assertThat(vc.type).isEqualTo("VerifiableCredential")

        val credentialSubject = vc.getCredentialSubject<Any>().asMap()

        Assertions.assertThat(credentialSubject["key1"]).isEqualTo("val1")
        Assertions.assertThat(credentialSubject["key2"]).isEqualTo("val2")
        Assertions.assertThat(vc.proof.verificationMethod).isEqualTo(issuer)

        val verifyResponse = irisSignerService.verifyVerifiableCredential().invoke(
            VaultVerifyVerifiableCredentialQuery(
                keyName = keyName,
                vc = vc
            )
        )

        Assertions.assertThat(verifyResponse.verified).isTrue()
    }
}
