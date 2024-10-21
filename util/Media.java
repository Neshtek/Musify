package util;

import java.util.*;
import extra.*;
import java.io.*;

public class Media {
    private String name;
    private String description;
    private ArrayList<String> creators;
    private int duration;
    private String captionFile;

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
        Media.checkCaptionExists(input);
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
    }

    public static void checkCaptionExists (String captionFile) {
        try {
            Scanner fileReader = new Scanner(new FileInputStream("data/mediatext/" + captionFile));
            fileReader.close();
        } catch (IOException e) {
            System.err.println("Invalid or missing caption file.");
        }
    }

    protected void setCreator(String creator) {
        this.creators.add(creator);
    }
}
