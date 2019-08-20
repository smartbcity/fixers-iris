package city.smartb.iris.api.rest

import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.messaging.tcp.reactor.ReactorNettyTcpClient
import org.springframework.web.bind.annotation.*
import java.time.Duration
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier
import java.util.HashMap



@RestController
class ConnectAPI(val amqpAdmin: AmqpAdmin, val template: RabbitTemplate) {

    private val DEFAULT_REPLY_TIMEOUT_MILLIS: Long = 600000

    private fun phoneQueue(id: String) = "$id.forPhone"

    final inline fun <reified T> typeReference() = object : ParameterizedTypeReference<T>() {}

    @GetMapping("/create")
    fun create(): String {
        return UUID.randomUUID().toString().also { uuid ->
            val args = hashMapOf("x-expires" to Duration.ofMinutes(15).toMillis()) as Map<String, Object>
            val phoneQueue = Queue(phoneQueue(uuid), true, false, true, args);
            amqpAdmin.declareQueue(phoneQueue)
        }
    }

    @PostMapping("/send/{id}")
    fun phoneConnect(@PathVariable id: String, @RequestBody value: String) {
        template.convertAndSend(phoneQueue(id), value);
    }

    @GetMapping("/get/{id}")
    fun handleReqDefResult(@PathVariable id: String): CompletableFuture<String?> {
        return CompletableFuture.supplyAsync((Supplier {
            this.template.receiveAndConvert(phoneQueue(id), DEFAULT_REPLY_TIMEOUT_MILLIS, typeReference<String>());
        }))
    }

    @GetMapping("/disconnect/{id}")
    fun disconnect(@PathVariable id: String): CompletableFuture<Boolean> {
        return CompletableFuture.supplyAsync((Supplier {
            amqpAdmin.deleteQueue(id)
        }))
    }

}