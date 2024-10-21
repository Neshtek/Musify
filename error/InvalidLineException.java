package error;

public class InvalidLineException extends Exception { 
    public InvalidLineException(String errorMessage) {
        super(errorMessage);
    }
}