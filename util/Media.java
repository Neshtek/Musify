package util;

import java.util.*;
import extra.*;
import java.io.*;

public class Media {
    private String name;
    private String description;
    private ArrayList<String> creators = new ArrayList<>();
    private int duration;
    private String captionFile;
    private String captions = "";

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

    public static Scanner checkCaptionExists (String captionFile) throws IOException {
        Scanner fileReader = new Scanner(new FileInputStream("data/mediatext/" + captionFile));
        return fileReader;
    }

    private void storeCaptions(Scanner fileReader) {
        while (fileReader.hasNextLine()) {
            this.captions += fileReader.nextLine() + "\n";
        }
    }

    protected void setCreator(String creator) {
        this.creators.add(creator);
    }

    protected String getName() {
        return this.name;
    }

    protected String getCreators() {
        return String.join(",", this.creators);
    }

    protected String getCreatorsForFile() {
        return String.join("#", this.creators);
    }

    protected String getDescription() {
        return this.description;
    }
    
    protected int getDuration() {
        return this.duration;
    }

    protected String getCaptions() {
        return this.captions;
    }

    protected String getCaptionFile() {
        return this.captionFile;
    }
}
