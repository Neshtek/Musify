package error;

public class InvalidFormatException extends Exception {
    public InvalidFormatException (String errorMessage) {
        super(errorMessage);
    }
}