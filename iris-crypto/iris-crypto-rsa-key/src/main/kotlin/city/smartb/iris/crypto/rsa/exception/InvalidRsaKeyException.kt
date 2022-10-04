package city.smartb.iris.crypto.rsa.exception

class InvalidRsaKeyException : Exception {
    constructor(msg: String?, e: Exception?) : super(msg, e) {}
    constructor(expMsg: String?) : super(expMsg) {}
}
