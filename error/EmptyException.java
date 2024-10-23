package error;

/**
 * This class defines a custom exception that is used when containers/list are empty, meaning they cant be iterated through.
 * @version ver 1.0
 * @author Neelaksh Tayal 1627659
 */
public class EmptyException extends Exception {
    public EmptyException (String errorMessage) {
        super(errorMessage);
    }
}
