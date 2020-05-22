package city.smartb.iris.jwt.exception;

public class InvalidJwtException extends Exception {
    public InvalidJwtException(String msg, Exception e) {
        super(msg, e);
    }

    public InvalidJwtException(String expMsg) {
        super(expMsg);
    }
}
