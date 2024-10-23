package extra;

import error.MediaNotFoundException;

/**
 * This interface defines a basic structure for the media-derived classes.
 * @version ver 1.0
 * @author Neelaksh Tayal 1627659
 */
public interface MediaType {
    /**
     * Method to display details of the media-derived class object.
     * @param index int value that contains the position of this object in the table.
     */
    public void displayDetails(int index);

    /**
     * Method to play the media-derived class object.
     * @throws MediaNotFoundException if caption file not found.
     */
    public void play() throws MediaNotFoundException;

    /**
     * Java standard toString method to convert all details of an object to string.
     * @return String value containing relevant details while printing.
     */
    public String toString();
}
