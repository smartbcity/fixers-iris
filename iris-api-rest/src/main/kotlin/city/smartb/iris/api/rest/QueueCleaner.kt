package city.smartb.iris.api.rest

import city.smartb.iris.api.rest.config.logger
import com.rabbitmq.http.client.ReactorNettyClient
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class QueueCleaner(val client: ReactorNettyClient) {

    val log by logger()

    @Scheduled(fixedRate = 300000)
    fun reportCurrentTime() {
        client.queues.map {
            val idle = LocalDateTime.parse(it.idleSince, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            var idleINMinutes = Duration.between(idle, LocalDateTime.now())
            idleINMinutes > Duration.ofMinutes(15)
            if (idleINMinutes > Duration.ofMinutes(15)) {
                log.info("Queue[${it.name}] created ${idleINMinutes} minutes ago will be deleted")
                client.deleteQueue(it.vhost, it.name).subscribe()
            }
        }
    }

}