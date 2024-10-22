package error;

public class MediaNotFoundException extends Exception {
    public MediaNotFoundException (String errorMessage) {
        super(errorMessage);
    }
}
