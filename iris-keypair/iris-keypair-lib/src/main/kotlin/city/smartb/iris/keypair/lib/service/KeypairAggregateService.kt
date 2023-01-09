package city.smartb.iris.keypair.lib.service

import city.smartb.iris.keypair.domain.KeypairCreateCommand
import city.smartb.iris.keypair.domain.KeypairCreatedEvent
import city.smartb.iris.vault.client.VaultClient
import city.smartb.iris.vault.domain.TransitKeyTypes
import city.smartb.iris.vault.domain.commands.TransitKeyAddCommand
import city.smartb.iris.vault.domain.queries.TransitPublicKeyGetQuery
import org.springframework.stereotype.Service

@Service
class KeypairAggregateService(
    private val vaultClient: VaultClient
) {
    suspend fun create(command: KeypairCreateCommand): KeypairCreatedEvent {
        vaultClient.transitKeyAdd(TransitKeyAddCommand(
            keyName = command.keyName,
            type = TransitKeyTypes.RSA_2048
        ))

        // TODO improve by directly returning pubkey from transitKeyAdd function
        val publicKey = vaultClient.transitPublicKeyGet(TransitPublicKeyGetQuery(command.keyName)).publicKey

        return KeypairCreatedEvent(
            id = command.keyName,
            publicKey = publicKey
        )
    }
}
