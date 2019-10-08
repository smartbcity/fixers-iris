package city.smartb.iris.api.rest.websocket

import city.smartb.iris.api.rest.model.MessageQuery
import city.smartb.iris.api.rest.model.MessageResponse
import city.smartb.iris.api.rest.features.session.SessionProvider
import city.smartb.iris.api.rest.features.messages.ApplicationHandler
import city.smartb.iris.api.rest.features.messages.SignerHandler
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class ApplicationReactiveWebSocketHandler(
        sessionProvider: SessionProvider,
        objectMapper: ObjectMapper,
        applicationHandler: ApplicationHandler)
    : AbstractReactiveWebSocketHandler<MessageQuery, MessageResponse, ApplicationHandler>(sessionProvider, objectMapper, applicationHandler)

@Component
class SignerReactiveWebSocketHandler(
        sessionProvider: SessionProvider,
        objectMapper: ObjectMapper,
        messagesHandler: SignerHandler)
    : AbstractReactiveWebSocketHandler<MessageResponse, MessageQuery, SignerHandler>(sessionProvider, objectMapper, messagesHandler)