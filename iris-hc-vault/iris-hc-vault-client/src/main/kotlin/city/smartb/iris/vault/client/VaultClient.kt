package city.smartb.iris.vault.client

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
import city.smartb.iris.vault.domain.http.ClientJvm
import city.smartb.iris.vault.domain.queries.SecretGet
import city.smartb.iris.vault.domain.queries.SecretGetQuery
import city.smartb.iris.vault.domain.queries.TransitPublicKeyGet
import city.smartb.iris.vault.domain.queries.TransitPublicKeyGetQuery
import org.springframework.vault.support.VaultResponse

class VaultClient(
    override var generateBearerToken: suspend () -> String? = { null },
    private val getVaultEntityId: suspend () -> String? = { null },
    vaultConfig: VaultConfig
): ClientJvm(vaultConfig.baseUrl) {

    companion object {
        const val SECRET_PATH = "v1/secret/data"
        const val TRANSIT_KEYS_PATH = "v1/transit/keys"
        const val TRANSIT_SIGN_PATH = "v1/transit/sign"
        const val TRANSIT_VERIFY_PATH = "v1/transit/verify"
        const val TRANSIT_SIGN_HASH_ALGORITHM = "sha2-256"
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
        val response = post<VaultResponse>(buildTransitSignPath(command.keyName), mapOf(
            "input" to command.input,
            "signature_algorithm" to "pkcs1v15",
            "prehashed" to false,
        ))

        val signature = response.data?.get("signature") as String
        return TransitSigned(signature)
    }

    suspend fun transitVerify(command: TransitVerifyCommand): TransitVerified {
        val response = post<VaultResponse>(buildTransitVerifyPath(command.keyName),
            mapOf("input" to command.input, "signature" to command.signature))
        return TransitVerified(response.data?.get("valid") as Boolean)
    }

    private suspend fun buildTransitKeysPath(keyName: String): String {
        return "$TRANSIT_KEYS_PATH/${vaultEntityId()}-$keyName"
    }

    private suspend fun buildTransitSignPath(keyName: String): String {
        return "$TRANSIT_SIGN_PATH/${vaultEntityId()}-$keyName/$TRANSIT_SIGN_HASH_ALGORITHM"
    }

    private suspend fun buildTransitVerifyPath(keyName: String): String {
        return "$TRANSIT_VERIFY_PATH/${vaultEntityId()}-$keyName"
    }

    private suspend fun buildSecretPath(secretPath: String): String {
        return "$SECRET_PATH/${vaultEntityId()}/$secretPath"
    }

    private suspend fun vaultEntityId(): String? = getVaultEntityId()
}
