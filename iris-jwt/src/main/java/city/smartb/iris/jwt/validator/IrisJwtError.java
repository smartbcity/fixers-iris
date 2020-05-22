package city.smartb.iris.jwt.validator;

import java.util.Objects;

public class IrisJwtError {

    private final String errorMsg;

    public static IrisJwtError form(String errorMsg){
        return new IrisJwtError(errorMsg);
    }

    public IrisJwtError(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static IrisJwtError form(String expMsg, Exception e) {
        return new IrisJwtError(expMsg + ": " + e.getMessage());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IrisJwtError that = (IrisJwtError) o;
        return Objects.equals(errorMsg, that.errorMsg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorMsg);
    }

    @Override
    public String toString() {
        return "IrisValidationError{" +
                "errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
