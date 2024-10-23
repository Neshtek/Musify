package error;

/**
 * This class defines a custom exception that is caught when any sort of media is not found.
 * @version ver 1.0
 * @author Neelaksh Tayal 1627659
 */
public class MediaNotFoundException extends Exception {
    public MediaNotFoundException (String errorMessage) {
        super(errorMessage);
    }
}
