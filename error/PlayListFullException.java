package error;

/**
 * This class defines a custom exception that is caught when a full playlist is being written to.
 * @version ver 1.0
 * @author Neelaksh Tayal 1627659
 */
public class PlayListFullException extends Exception {
    public PlayListFullException (String errorMessage) {
        super(errorMessage);
    }
}