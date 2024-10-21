package error;

public class PlayListFullException extends Exception {
    public PlayListFullException (String errorMessage) {
        super(errorMessage);
    }
}