package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.features.CreateResponse
import city.smartb.iris.api.rest.features.CreateSessionCommand
import city.smartb.iris.api.rest.model.Session
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

@RestController
class CreateEndpoints(
        private val createSessionCommand: CreateSessionCommand,
        private val amqpAdmin: AmqpAdmin,
        private val template: RabbitTemplate) {

    private val DEFAULT_REPLY_TIMEOUT_MILLIS: Long = 600000

    final inline fun <reified T> typeReference() = object : ParameterizedTypeReference<T>() {}

    @GetMapping("/create")
    fun create(): Mono<CreateResponse> = createSessionCommand.execute()

    @PostMapping("/send/{id}")
    fun phoneConnect(@PathVariable id: String, @RequestBody value: String) {
        template.convertAndSend(Session(id).getQueueToSendToPhone(), value);
    }

    @GetMapping("/get/{id}")
    fun handleReqDefResult(@PathVariable id: String): CompletableFuture<String?> {
        return CompletableFuture.supplyAsync((Supplier {
            this.template.receiveAndConvert(Session(id).getQueueToSendToPhone(), DEFAULT_REPLY_TIMEOUT_MILLIS, typeReference<String>());
        }))
    }

    @GetMapping("/disconnect/{id}")
    fun disconnect(@PathVariable id: String): CompletableFuture<Boolean> {
        return CompletableFuture.supplyAsync((Supplier {
            amqpAdmin.deleteQueue(id)
        }))
    }

}