package util;

import java.util.*;
import extra.*;
import java.io.*;

/**
 * This class is for all Playlist relevant data.
 * @version ver 1.0
 * @author Neelaksh Tayal 1627659
 */
public abstract class Media {
    private String name;
    private String description;
    private ArrayList<String> creators = new ArrayList<>();
    private int duration;
    private String captionFile;
    private String captions = "";

    /**
     * Default Constructor. Creates a new media object by taking in values from the user.
     */
    public Media() {
        String input;
        System.out.print("Enter the title: ");
        input = Constants.keyboard.nextLine();
        this.name = input;
        System.out.print("Enter the description: ");
        input = Constants.keyboard.nextLine();
        this.description = input;
        System.out.print("Enter duration in mins: ");
        input = Constants.keyboard.nextLine();
        this.duration = Integer.parseInt(input);
        System.out.print("Enter the filename for the captions or lyrics: ");
        input = Constants.keyboard.nextLine();
        this.captionFile = input;
        try {
            Scanner fileReader = Media.checkCaptionExists(input);
            this.captions = "";
            this.storeCaptions(fileReader);
            fileReader.close();
        } catch (IOException e) {
            System.err.println("Invalid or missing caption file.");
        }
    }

    /**
     * Creates a new Media object based on passed parameters.
     * @param name String object that contains the name of the media item.
     * @param description String object that contains a description of the media item.
     * @param creators String object that contains the name of the creators of the media item separated by '#'.
     * @param duration String object that contains a numeric duration.
     * @param captionFile String object that contains the name of the caption file.
     */
    public Media (String name, String description, String creators, String duration, String captionFile) {
        this.name = name;
        this.description = description;
        this.creators = new ArrayList<>();
        String[] creatorList = creators.split("#");
        for (String creator : creatorList)
            this.creators.add(creator);
        this.duration = Integer.parseInt(duration);
        this.captionFile = captionFile;
        try {
            Scanner fileReader = Media.checkCaptionExists(captionFile);
            this.captions = "";
            this.storeCaptions(fileReader);
            fileReader.close();
        } catch (IOException e) {
            System.err.println("Invalid or missing caption file.");
        }
    }

    /**
     * Method to check caption file exists.
     * @param captionFile the name of the caption file.
     * @return Scanner object to maintain reader consistency.
     * @throws IOException if file cannot be opened/read.
     */
    public static Scanner checkCaptionExists (String captionFile) throws IOException {
        Scanner fileReader = new Scanner(new FileInputStream("data/mediatext/" + captionFile));
        return fileReader;
    }

    /**
     * Method to store the captions in the file as a string.
     * @param fileReader Scanner object that has the file open.
     */
    private void storeCaptions(Scanner fileReader) {
        while (fileReader.hasNextLine()) {
            this.captions += fileReader.nextLine() + "\n";
        }
    }

    /**
     * Method to add a String creator to creators.
     * @param creator String value that contains name of a creator.
     */
    protected void setCreator(String creator) {
        this.creators.add(creator);
    }

    /**
     * Method to get the name of the media item.
     * @return String value that contains name.
     */
    protected String getName() {
        return this.name;
    }

    /**
     * Method to get string of all creators.
     * @return String value that contains all creators separated by ','.
     */
    protected String getCreators() {
        return String.join(",", this.creators);
    }

    /**
     * Method to get String value of all creators for writing to file.
     * @return String value that contains all creators separated by '#'.
     */
    protected String getCreatorsForFile() {
        return String.join("#", this.creators);
    }

    /**
     * Method to get the description of the media item.
     * @return String value that contains the description.
     */
    protected String getDescription() {
        return this.description;
    }
    
    /**
     * Method to get duration of media item.
     * @return int value that contains the duration.
     */
    protected int getDuration() {
        return this.duration;
    }

    /**
     * Method to get captions of the media item.
     * @return String value that contains the captions.
     */
    protected String getCaptions() {
        return this.captions;
    }

    /**
     * Method to get the caption file name.
     * @return String value that contains the caption file name.
     */
    protected String getCaptionFile() {
        return this.captionFile;
    }
}
