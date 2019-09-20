package city.smartb.iris.api.rest.websocket

import city.smartb.iris.api.rest.ApplicationHandler
import city.smartb.iris.api.rest.SignerHandler
import city.smartb.iris.api.rest.model.MessageQuery
import city.smartb.iris.api.rest.model.MessageResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class ApplicationReactiveWebSocketHandler(objectMapper: ObjectMapper,
                                          applicationHandler: ApplicationHandler)
    : AbstractReactiveWebSocketHandler<MessageQuery, MessageResponse, ApplicationHandler>(objectMapper, applicationHandler)

@Component
class SignerReactiveWebSocketHandler(objectMapper: ObjectMapper,
                                     messagesHandler: SignerHandler)
    : AbstractReactiveWebSocketHandler<MessageResponse, MessageQuery, SignerHandler>(objectMapper, messagesHandler)