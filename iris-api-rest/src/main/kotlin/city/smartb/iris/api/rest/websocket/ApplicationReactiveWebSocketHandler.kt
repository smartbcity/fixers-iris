package city.smartb.iris.api.rest.websocket

import city.smartb.iris.api.rest.model.MessageQuery
import city.smartb.iris.api.rest.model.MessageResponse
import city.smartb.iris.api.rest.features.session.ChannelProvider
import city.smartb.iris.api.rest.features.messages.ApplicationHandler
import city.smartb.iris.api.rest.features.messages.SignerHandler
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class ApplicationReactiveWebSocketHandler(
        channelProvider: ChannelProvider,
        objectMapper: ObjectMapper,
        applicationHandler: ApplicationHandler)
    : AbstractReactiveWebSocketHandler<MessageQuery, MessageResponse, ApplicationHandler>(channelProvider, objectMapper, applicationHandler)

@Component
class SignerReactiveWebSocketHandler(
        channelProvider: ChannelProvider,
        objectMapper: ObjectMapper,
        messagesHandler: SignerHandler)
    : AbstractReactiveWebSocketHandler<MessageResponse, MessageQuery, SignerHandler>(channelProvider, objectMapper, messagesHandler)