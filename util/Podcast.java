package util;

import java.util.*;

public class Podcast extends Media {
    private String category;
    private String seriesName;
    private int episodeNo;

    public Podcast() {};

    public Podcast (String name, String description, ArrayList<String> creators, String category, String seriesName, int episodeNo, int duration, String captionFile) {
        super(name, description, creators, duration, captionFile);
        this.category = category;
        this.seriesName = seriesName;
        this.episodeNo = episodeNo;
    }
}
