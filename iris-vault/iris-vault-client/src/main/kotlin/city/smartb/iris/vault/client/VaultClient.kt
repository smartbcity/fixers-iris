package city.smartb.iris.vault.client

import city.smartb.i2.spring.boot.auth.AuthenticationProvider
import city.smartb.im.commons.http.ClientJvm
import city.smartb.iris.vault.client.config.VaultConfig
import city.smartb.iris.vault.client.exception.TransitKeyTypeNotFoundException
import city.smartb.iris.vault.domain.TransitKeyTypes
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
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.vault.support.VaultResponse

@Service
class VaultClient(
    vaultConfig: VaultConfig
): ClientJvm(vaultConfig.baseUrl) {

    companion object {
        const val LOGIN_PATH = "v1/auth/jwt/login"
        const val SECRET_PATH = "v1/secret/data"
        const val TRANSIT_KEYS_PATH = "v1/transit/keys"
        const val TRANSIT_SIGN_PATH = "v1/transit/sign"
        const val TRANSIT_VERIFY_PATH = "v1/transit/verify"
    }

    /**
     * KV secret
     */
    suspend fun secretGet(query: SecretGetQuery): SecretGet {
        return SecretGet(
            get<VaultResponse>(buildSecretPath(query.path)).data?.get("data") as Map<String, Any>
        )
    }

    suspend fun secretAdd(command: SecretAddCommand): SecretAdded {
        val data = mapOf("data" to command.data)
        return SecretAdded(
            post<VaultResponse>(buildSecretPath(command.path), data).data as Map<String, Any>
        )
    }

    /**
     * Transit
     */
    suspend fun transitKeyAdd(command: TransitKeyAddCommand): TransitKeyAdded {
        if (!TransitKeyTypes.toList().contains(command.type)) {
            throw TransitKeyTypeNotFoundException(command.type)
        }
        post<Any>(buildTransitKeysPath(command.keyName), mapOf("type" to command.type))
        return TransitKeyAdded()
    }

    suspend fun transitPublicKeyGet(query: TransitPublicKeyGetQuery): TransitPublicKeyGet {
        val response = get<VaultResponse>(buildTransitKeysPath(query.keyName)).data!!
        val keyType = response.get("type") as String
        val lastVersion = response.get("latest_version") as Int
        val pubKeyValue = (response["keys"] as Map<String, Map<String, String>>)["$lastVersion"]?.get("public_key") as String
        return TransitPublicKeyGet(
            type = keyType,
            publicKey = pubKeyValue
        )
    }

    suspend fun transitSign(command: TransitSignCommand): TransitSigned {
        val response = post<VaultResponse>(buildTransitSignPath(command.keyName), mapOf("input" to command.input))
        val signature = response.data?.get("signature") as String
        return TransitSigned(signature)
    }

    suspend fun transitVerify(command: TransitVerifyCommand): TransitVerified {
        val response = post<VaultResponse>(buildTransitVerifyPath(command.keyName), mapOf("input" to command.input, "signature" to command.signature))
        return TransitVerified(response.data?.get("valid") as Boolean)
    }

    /**
     * Authentication
     */
    suspend fun getVaultEntityId(): String? {
        val jwt = AuthenticationProvider.getPrincipal()?.tokenValue
        val body = mapOf("role" to "user", "jwt" to jwt)
        val response = post<VaultResponse>(LOGIN_PATH, body as Any, false)
        return response.requiredAuth["entity_id"] as String?
    }

    override var generateBearerToken = suspend {
        val jwt = AuthenticationProvider.getPrincipal()?.tokenValue
        val body = mapOf("role" to "user", "jwt" to jwt)
        val response = post<VaultResponse>(LOGIN_PATH, body as Any, false)
        response.requiredAuth["client_token"] as String?
    }

    private suspend fun buildTransitKeysPath(keyName: String): String {
        return "$TRANSIT_KEYS_PATH/${getVaultEntityId()}-$keyName"
    }

    private suspend fun buildTransitSignPath(keyName: String): String {
        return "$TRANSIT_SIGN_PATH/${getVaultEntityId()}-$keyName"
    }

    private suspend fun buildTransitVerifyPath(keyName: String): String {
        return "$TRANSIT_VERIFY_PATH/${getVaultEntityId()}-$keyName"
    }

    private suspend fun buildSecretPath(secretPath: String): String {
        return "$SECRET_PATH/${getVaultEntityId()}/$secretPath"
    }


    /**
     * Utils dev function
     */
    suspend fun genToken(): String? {
        val jwt = AuthenticationProvider.getPrincipal()?.tokenValue
        val body = mapOf("role" to "user", "jwt" to jwt)
        val response = post<VaultResponse>(LOGIN_PATH, body as Any, false)
        return response.requiredAuth["client_token"] as String?
    }
}
