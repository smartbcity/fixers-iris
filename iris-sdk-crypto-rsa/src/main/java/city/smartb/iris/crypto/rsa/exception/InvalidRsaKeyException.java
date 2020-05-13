package city.smartb.iris.crypto.rsa.exception;

public class InvalidRsaKeyException extends Exception {
    public InvalidRsaKeyException(String msg, Exception e) {
        super(msg, e);
    }

    public InvalidRsaKeyException(String expMsg) {
        super(expMsg);
    }
}
