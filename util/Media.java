package util;

import java.util.*;

public class Media {
    private String name;
    private String description;
    private ArrayList<String> creators;
    private int duration;
    private String captionFile;

    public Media() {};

    public Media (String name, String description, ArrayList<String> creators, int duration, String captionFile) {
        this.name = name;
        this.description = description;
        this.creators = new ArrayList<>(creators);
        this.duration = duration;
        this.captionFile = captionFile;
    }
}
