package city.smartb.iris.api.rest.model

class Session(
        val id: String
) {

    fun getQueueToSendToPhone() =  "$id.forPhone"
    fun getQueueToSendToBrowser() =  "$id.forBrowser"
}