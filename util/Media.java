package util;

import java.util.*;

public class Media {
    private String name;
    private String description;
    private ArrayList<String> creators;
    private int duration;
    private String captionFile;

    public Media() {};

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
}
