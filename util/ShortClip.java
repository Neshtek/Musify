package util;

import error.MediaNotFoundException;
import extra.*;

/**
 * This class is for all ShortClip data extended over Media and implementing the MediaType interface.
 * @version ver 1.0
 * @author Neelaksh Tayal 1627659
 */
public class ShortClip extends Media implements MediaType {
    /**
     * Default Constructor. Creates a new shortclip object by taking in values from the user.
     */
    public ShortClip() {
        super();
        System.out.print("Enter the artist Name: ");
        String input = Constants.keyboard.nextLine();
        super.setCreator(input);
    }

    /**
     * Creates a new ShortClip object based on file data.
     * @param args file data passed as a String array.
     */
    public ShortClip (String[] args) {
        super(args[0], args[1], args[2], args[3], args[4]);
    }

    public void displayDetails(int index) {
        System.out.printf(Constants.SHORTCLIP_DATA_FORMATTER, (index + 1), super.getName(), super.getCreators(), super.getDescription(), super.getDuration());
    }

    public void play() throws MediaNotFoundException {
        System.out.printf("Playing short clip: %s by %s for %d mins.\n", super.getName(), super.getCreators(), super.getDuration());
        if (super.getCaptions().equalsIgnoreCase(""))
            throw new MediaNotFoundException("Cannot show captions for short clip. Media not found.");
        else {
            System.out.println("Here are the contents of the short clip.");
            System.out.print(super.getCaptions());
        }
    }

    public String toString() {
        return String.join(",", super.getName(), super.getDescription(), super.getCreatorsForFile(), Integer.toString(super.getDuration()), super.getCaptionFile());
    }
}
