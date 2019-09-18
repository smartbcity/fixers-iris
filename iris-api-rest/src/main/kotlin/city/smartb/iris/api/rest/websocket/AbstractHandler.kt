package city.smartb.iris.api.rest.websocket

import city.smartb.iris.api.rest.model.Session
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
abstract class AbstractHandler<RECEIVE_FROM_DEVICE, SEND_TO_DEVICE> {

    abstract fun receiveFromDevice(session: Session, value: RECEIVE_FROM_DEVICE);
    abstract fun sendToDevice(session: Session): Flux<SEND_TO_DEVICE>

    abstract fun toValue(json: ByteArray): RECEIVE_FROM_DEVICE
    fun toValue(json: String): RECEIVE_FROM_DEVICE {
        return toValue(json.toByteArray())
    }


}