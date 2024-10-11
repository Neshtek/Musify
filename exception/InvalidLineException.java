package exception;

public class InvalidLineException extends Exception { 
    public InvalidLineException(String errorMessage) {
        super(errorMessage);
    }
}