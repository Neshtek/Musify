package error;

/**
 * This class defines a custom exception that is caught when format mismatch occurs.
 * @version ver 1.0
 * @author Neelaksh Tayal 1627659
 */
public class InvalidFormatException extends Exception {
    public InvalidFormatException (String errorMessage) {
        super(errorMessage);
    }
}