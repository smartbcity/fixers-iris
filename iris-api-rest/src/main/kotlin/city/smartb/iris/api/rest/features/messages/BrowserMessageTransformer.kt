package city.smartb.iris.api.rest.features.messages

import city.smartb.iris.api.rest.config.logger
import city.smartb.iris.api.rest.model.*
import org.springframework.stereotype.Service

@Service
class BrowserMessageTransformer: MessageTransformer<MessageQuery> {

    private val log by logger()

    override fun transform(channelSession: ChannelSession, message: MessageQuery) : MessageQuery {
        log.info("Receive message from browser to phone[${message}]")
        return when (message.action) {
            ActionType.AUTH -> PubKeyMessageQuery()
            else -> message
        }
    }

}