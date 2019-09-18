package city.smartb.iris.api.rest.websocket

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.HandlerMapping
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean

@Configuration
class ReactiveWebSocketConfiguration {

    @Autowired
    private lateinit var browserHandler: BrowserReactiveWebSocketHandler

    @Autowired
    private lateinit var responseHanlder: PhoneReactiveWebSocketHandler


    @Bean
    fun webSocketHandlerMapping(): HandlerMapping {
        val map =  mapOf(
                "/connect/application/*" to browserHandler,
                "/connect/signer/*" to responseHanlder

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