package city.smartb.iris.jsonld.exception;

public class JsonLdDereferencingException extends RuntimeException {
    public JsonLdDereferencingException() {
    }

    public JsonLdDereferencingException(String message) {
        super(message);
    }

    public JsonLdDereferencingException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonLdDereferencingException(Throwable cause) {
        super(cause);
    }

    public JsonLdDereferencingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
