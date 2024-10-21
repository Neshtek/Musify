package util;

import java.util.*;

public class Playlist <T extends Media> {
    private String name;
    private String mediaType;
    private String fileName;
    private ArrayList<T> media;

    public Playlist() {};

    public Playlist (String[] args) {
        this.name = args[0];
        this.mediaType = args[1];
        this.fileName = args[2];
        this.media = new ArrayList<T>();

    }

    public void addMedia(T mediaObject) {
        this.media.add(mediaObject);
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getMediaType() {
        return this.mediaType;
    }
}