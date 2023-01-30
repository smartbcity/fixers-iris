package city.smartb.iris.vault.client

import city.smartb.i2.spring.boot.auth.AuthenticationProvider
import city.smartb.im.commons.http.ClientJvm
import city.smartb.iris.vault.client.config.VaultConfig
import org.springframework.stereotype.Service
import org.springframework.vault.support.VaultResponse

@Service
class VaultAuthClient(
    vaultConfig: VaultConfig
): ClientJvm(vaultConfig.baseUrl) {

    companion object {
        const val LOGIN_PATH = "v1/auth/jwt/login"
    }

    suspend fun getVaultEntityId(): String? {
        val jwt = AuthenticationProvider.getPrincipal()?.tokenValue
        val body = mapOf("role" to "user", "jwt" to jwt)
        val response = post<VaultResponse>(LOGIN_PATH, body as Any, false)
        return response.requiredAuth["entity_id"] as String?
    }

    suspend fun generateToken(): String? {
        val jwt = AuthenticationProvider.getPrincipal()?.tokenValue
        val body = mapOf("role" to "user", "jwt" to jwt)
        val response = post<VaultResponse>(LOGIN_PATH, body as Any, false)
        return response.requiredAuth["client_token"] as String?
    }
}
