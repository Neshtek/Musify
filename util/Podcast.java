package util;

import java.util.*;

public class Podcast extends Media {
    private String category;
    private String seriesName;
    private int episodeNo;

    public Podcast() {};

    public Podcast (String[] args) {
        super(args[0], args[1], args[2], args[6], args[7]);
        this.category = args[3];
        this.seriesName = args[4];
        this.episodeNo = Integer.parseInt(args[5]);
    }
}
