package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.features.messages.BrowserMessageTransformer
import city.smartb.iris.api.rest.features.session.*
import city.smartb.iris.api.rest.model.ChannelId
import city.smartb.iris.api.rest.features.sim.SimService
import city.smartb.iris.api.rest.model.MessageQuery
import city.smartb.iris.api.rest.model.SimChannelId
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

@RestController
class ChannelEndpoint(
        private val browserMessageTransformer: BrowserMessageTransformer,
        private val simService: SimService,
        private val channelProvider: ChannelProvider,
        private val getChannelCommand: GetChannelCommand,
        private val createChannelCommand: CreateChannelCommand,
        private val deleteChannelCommand: DeleteChannelCommand,
        private val template: RabbitTemplate) {

    private val DEFAULT_REPLY_TIMEOUT_MILLIS: Long = 600000

    final inline fun <reified T> typeReference() = object : ParameterizedTypeReference<T>() {}

    @PostMapping("/channels")
    fun create(): Mono<ChannelResponse> = createChannelCommand.execute()

    @GetMapping("/channels/{channelId}")
    fun get(@PathVariable channelId: ChannelId): Mono<ChannelResponse> = getChannelCommand.execute(channelId)

    @PutMapping("/channels/{channelId}")
    fun connectPhoneNumber(@PathVariable channelId: String, @RequestParam("phoneNumber") phoneNumber: String): Mono<SimChannelId> {
        val channelSession = channelProvider.fromChannelId(ChannelId(channelId))
        return simService.start(channelSession, phoneNumber)
    }

    @DeleteMapping("/channels/{channelId}")
    fun delete(@PathVariable channelId: String): Mono<Boolean> {
        return deleteChannelCommand.execute(ChannelId(channelId))
    }


    @GetMapping("/channels/{channelId}/messages")
    fun handleReqDefResult(@PathVariable channelId: String): CompletableFuture<String?> {
        return CompletableFuture.supplyAsync((Supplier {
            val channelSession = channelProvider.fromChannelId(ChannelId(channelId))
            this.template.receiveAndConvert(channelSession.getQueueToSendToSigner(), DEFAULT_REPLY_TIMEOUT_MILLIS, typeReference<String>());
        }))
    }

    @PostMapping("/channels/{id}/messages")
    fun sendMessage(@PathVariable id: String, @RequestBody value: MessageQuery) {
        val channelSession = channelProvider.fromChannelId(ChannelId(id))
        val msgToSend = browserMessageTransformer.transform(channelSession, value)
        template.convertAndSend(channelSession.getQueueToSendToSigner(), msgToSend);
    }

}