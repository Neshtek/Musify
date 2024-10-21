package util;

import java.util.*;

public class Playlist <T extends Media> {
    private String name;
    private String mediaType;
    private String fileName;
    private ArrayList<T> media;

    public Playlist() {};

    public Playlist (String name, String mediaType, String fileName) {
        this.name = name;
        this.mediaType = mediaType;
        this.fileName = fileName;
        this.media = new ArrayList<T>();
    }

    public String getFileName() {
        return this.fileName;
    }
}