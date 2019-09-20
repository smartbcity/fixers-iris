package city.smartb.iris.api.rest.config

import city.smartb.iris.api.rest.websocket.ApplicationReactiveWebSocketHandler
import city.smartb.iris.api.rest.websocket.SignerReactiveWebSocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.HandlerMapping
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean

@Configuration
class ReactiveWebSocketConfiguration {

    @Autowired
    private lateinit var applicationHandler: ApplicationReactiveWebSocketHandler

    @Autowired
    private lateinit var signerHanlder: SignerReactiveWebSocketHandler


    @Bean
    fun webSocketHandlerMapping(): HandlerMapping {
        val map =  mapOf(
                "/connect/application/*" to applicationHandler,
                "/connect/signer/*" to signerHanlder

        )
        return SimpleUrlHandlerMapping().apply {
            this.order = 1
            this.urlMap = map
        }
    }

    @Bean
    fun handlerAdapter(): WebSocketHandlerAdapter {
        return WebSocketHandlerAdapter()
    }
}