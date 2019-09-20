package city.smartb.iris.api.rest.websocket

import city.smartb.iris.api.rest.ApplicationHandler
import city.smartb.iris.api.rest.SignerHandler
import city.smartb.iris.api.rest.model.Message
import city.smartb.iris.api.rest.model.Response
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class ApplicationReactiveWebSocketHandler(objectMapper: ObjectMapper,
                                          applicationHandler: ApplicationHandler)
    : AbstractReactiveWebSocketHandler<Message, Response, ApplicationHandler>(objectMapper, applicationHandler)

@Component
class SignerReactiveWebSocketHandler(objectMapper: ObjectMapper,
                                     messagesHandler: SignerHandler)
    : AbstractReactiveWebSocketHandler<Response, Message, SignerHandler>(objectMapper, messagesHandler)