package city.smartb.iris.api.rest.websocket

import city.smartb.iris.api.rest.BrowserHandler
import city.smartb.iris.api.rest.PhoneHandler
import city.smartb.iris.api.rest.model.Message
import city.smartb.iris.api.rest.model.Response
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class BrowserReactiveWebSocketHandler(objectMapper: ObjectMapper,
                                      browserHandler: BrowserHandler)
    : AbstractReactiveWebSocketHandler<Message, Response, BrowserHandler>(objectMapper, browserHandler)

@Component
class PhoneReactiveWebSocketHandler(objectMapper: ObjectMapper,
                                    messagesHandler: PhoneHandler)
    : AbstractReactiveWebSocketHandler<Response, Message, PhoneHandler>(objectMapper, messagesHandler)