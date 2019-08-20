package city.smartb.iris.api.rest.config

import com.rabbitmq.http.client.Client
import com.rabbitmq.http.client.ReactorNettyClient
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitSimpleConfig {

    @Value("\${spring.rabbitmq.password}")
    lateinit var rabbitmqPassword: String

    @Value("\${spring.rabbitmq.rest}")
    lateinit var restUrl: String

    @Bean
    fun amqpAdmin(connectionFactory: ConnectionFactory): AmqpAdmin {
        return RabbitAdmin(connectionFactory)
    }

    @Bean
    fun nettyClient(connectionFactory: ConnectionFactory): ReactorNettyClient {
        return ReactorNettyClient(restUrl, connectionFactory.username, rabbitmqPassword)
    }

    @Bean
    fun client(connectionFactory: ConnectionFactory): Client {
        return Client(restUrl, connectionFactory.username, rabbitmqPassword)
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        var rabbit = RabbitTemplate(connectionFactory)
        rabbit.messageConverter = Jackson2JsonMessageConverter()
        return rabbit;
    }

}