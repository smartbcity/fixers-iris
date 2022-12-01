package city.smartb.iris.vault.client

import city.smartb.iris.vault.domain.commands.SecretAddCommand
import city.smartb.iris.vault.domain.commands.SecretAdded
import city.smartb.iris.vault.domain.commands.TransitKeyAddCommand
import city.smartb.iris.vault.domain.commands.TransitKeyAdded
import city.smartb.iris.vault.domain.commands.TransitSignCommand
import city.smartb.iris.vault.domain.commands.TransitSigned
import city.smartb.iris.vault.domain.commands.TransitVerified
import city.smartb.iris.vault.domain.commands.TransitVerifyCommand
import city.smartb.iris.vault.domain.queries.SecretGet
import city.smartb.iris.vault.domain.queries.SecretGetQuery
import city.smartb.iris.vault.domain.queries.TransitPublicKeyGet
import city.smartb.iris.vault.domain.queries.TransitPublicKeyGetQuery
import org.springframework.vault.support.VaultResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val vaultClient: VaultClient
) {

    /**
     * KV Secret store
     */
    @PostMapping("/secretAdd")
    suspend fun secretAdd(@RequestBody command: SecretAddCommand): SecretAdded {
        return vaultClient.secretAdd(command)
    }

    @PostMapping("/secretGet")
    suspend fun secretGet(@RequestBody query: SecretGetQuery): SecretGet {
        return vaultClient.secretGet(query)
    }

    /**
     * Transit
     */
    @PostMapping("/transitKeyAdd")
    suspend fun transitKeyAdd(@RequestBody command: TransitKeyAddCommand): TransitKeyAdded {
        return vaultClient.transitKeyAdd(command)
    }

    @PostMapping("/transitPublicKeyGet")
    suspend fun transitPublicKeyGet(@RequestBody query: TransitPublicKeyGetQuery): TransitPublicKeyGet {
        return vaultClient.transitPublicKeyGet(query)
    }

    @PostMapping("/transitSign")
    suspend fun transitSign(@RequestBody command: TransitSignCommand): TransitSigned {
        return vaultClient.transitSign(command)
    }

    @PostMapping("/transitVerify")
    suspend fun transitVerify(@RequestBody command: TransitVerifyCommand): TransitVerified {
        return vaultClient.transitVerify(command)
    }

    @GetMapping("/transitKeyList")
    suspend fun transitKeyList(): VaultResponse {
        return vaultClient.t()
    }

    /**
     * Utils dev function
     */
    @GetMapping("/token")
    suspend fun generateToken(): String? {
        return vaultClient.genToken()
    }

    @GetMapping("/entity")
    suspend fun getEntityId(): String? {
        return vaultClient.getVaultEntityId()
    }
}
