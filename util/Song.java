package util;

import java.util.*;

public class Song extends Media {
    private String genre;

    public Song (String name, String description, ArrayList<String> creators, String genre, int duration, String captionFile) {
        super(name, description, creators, duration, captionFile);
        this.genre = genre;
    }
}
