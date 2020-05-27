package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.config.logger
import city.smartb.iris.api.rest.exception.InvalidValueException
import city.smartb.iris.api.rest.features.session.CreateChannelResponse
import city.smartb.iris.api.rest.model.*
import city.smartb.iris.api.rest.features.session.ChannelProvider
import city.smartb.iris.api.rest.features.messages.SignerHandler
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
class SimEndpoint(
        private val channelProvider: ChannelProvider,
        private val signerHandler: SignerHandler) {

    val log by logger()

    @GetMapping("/sim/{phoneNumber}/{phoneChannelId}/{actionType}/{payload}")
    fun create(
            @PathVariable phoneNumber: String,
            @PathVariable phoneChannelId: Int,
            @PathVariable actionType: Int,
            @PathVariable payload: String
    ): Mono<CreateChannelResponse> {
        log.info("Handle sim response phoneNumber[${phoneNumber}] phoneChannelId[${phoneChannelId}] actionType[${actionType}] payload[${payload}]")
        val message = when(ActionType.valueOf(actionType)){
            ActionType.PUB_KEY -> PublicKeyResponse(payload)
            ActionType.SIGN -> SignResponse(payload)
            ActionType.SIGN_PUB_KEY -> SignPublicKeyResponse(payload)
            else -> throw InvalidValueException("Invalid actionType[${actionType}] sent by phone")
        }

        val simChannelId = SimChannelId(phoneNumber = phoneNumber, phoneChannelId = phoneChannelId)
        val channelSession = channelProvider.fromSimChannelId(simChannelId)
                ?: throw InvalidValueException("No session found from phoneNumber[${phoneNumber}] phoneChannelId[${phoneChannelId}]")

        signerHandler.receiveFromDevice(channelSession, message)
        return Mono.empty()
    }


}