package error;

/**
 * This class defines a custom exception that is caught when there aren't sufficient data points in the file being read.
 * @version ver 1.0
 * @author Neelaksh Tayal 1627659
 */
public class InvalidLineException extends Exception { 
    public InvalidLineException (String errorMessage) {
        super(errorMessage);
    }
}