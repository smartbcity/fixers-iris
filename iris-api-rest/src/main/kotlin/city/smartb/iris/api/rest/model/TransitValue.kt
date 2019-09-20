package city.smartb.iris.api.rest.model

interface TransitValue {

    fun isMessage(): Boolean{
        return this.javaClass is Message
    }

    fun isResponse(): Boolean {
        return this.javaClass is Response
    }
}
