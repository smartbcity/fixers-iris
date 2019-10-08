package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.features.session.CreateSessionResponse
import city.smartb.iris.api.rest.features.session.CreateSessionCommand
import city.smartb.iris.api.rest.model.ChannelId
import city.smartb.iris.api.rest.features.session.SessionProvider
import city.smartb.iris.api.rest.features.sim.SimService
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

@RestController
class CreateEndpoint(
        private val simService: SimService,
        private val sessionProvider: SessionProvider,
        private val createSessionCommand: CreateSessionCommand,
        private val template: RabbitTemplate) {

    private val DEFAULT_REPLY_TIMEOUT_MILLIS: Long = 600000

    final inline fun <reified T> typeReference() = object : ParameterizedTypeReference<T>() {}

    @PostMapping("/channels")
    fun create(): Mono<CreateSessionResponse> = createSessionCommand.execute()

    @PutMapping("/channels/{channelId}")
    fun connectPhoneNumber(@PathVariable channelId: String, @RequestParam("phoneNumber") phoneNumber: String) {
        val channelSession = sessionProvider.fromChannelId(ChannelId(channelId))
        simService.start(channelSession, phoneNumber)
    }

    @GetMapping("/channels/{channelId}/messages")
    fun handleReqDefResult(@PathVariable channelId: String): CompletableFuture<String?> {
        return CompletableFuture.supplyAsync((Supplier {
            val channelSession = sessionProvider.fromChannelId(ChannelId(channelId))
            this.template.receiveAndConvert(channelSession.getQueueToSendToSigner(), DEFAULT_REPLY_TIMEOUT_MILLIS, typeReference<String>());
        }))
    }

    @PostMapping("/channels/{id}/messages")
    fun sendMessage(@PathVariable id: String, @RequestBody value: String) {
        val channelSession = sessionProvider.fromChannelId(ChannelId(id))
        template.convertAndSend(channelSession.getQueueToSendToSigner(), value);
    }

}