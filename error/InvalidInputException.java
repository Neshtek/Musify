package error;

/**
 * This class defines a custom exception that is caught when user entered value is invalid.
 * @version ver 1.0
 * @author Neelaksh Tayal 1627659
 */
public class InvalidInputException extends Exception {
    public InvalidInputException (String errorMessage) {
        super(errorMessage);
    }
}
