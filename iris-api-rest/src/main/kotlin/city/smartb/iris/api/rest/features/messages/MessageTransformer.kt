package city.smartb.iris.api.rest.features.messages

import city.smartb.iris.api.rest.model.ChannelSession

interface MessageTransformer<T> {
    fun transform(channelSession: ChannelSession, message: T) : T
}