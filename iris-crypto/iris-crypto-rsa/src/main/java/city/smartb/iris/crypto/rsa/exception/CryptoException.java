package city.smartb.iris.crypto.rsa.exception;

public class CryptoException extends Exception {
    public CryptoException(String msg, Exception e) {
        super(msg, e);
    }

    public CryptoException(String expMsg) {
        super(expMsg);
    }
}
