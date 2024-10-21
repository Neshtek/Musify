package util;

import java.util.*;
import error.*;
import extra.*;

public class Playlist {
    private String name;
    private String mediaType;
    private String fileName;
    private ArrayList<Song> songs;
    private ArrayList<Podcast> podcasts;
    private ArrayList<ShortClip> shortClips;

    public Playlist() {
        String input;
        System.out.print("Enter Playlist Name: ");
        input = Constants.keyboard.nextLine();
        this.name = input;
        System.out.print("Enter Playlist Type: ");
        input = Constants.keyboard.nextLine();
        this.mediaType = input.toUpperCase();
        System.out.print("Enter a filename to save the playlist: ");
        input = Constants.keyboard.nextLine();
        this.fileName = input;
        System.out.printf("Add some %s to your Playlist.\n", this.mediaType);
        do {
            System.out.printf("Enter A to add a %s to the playlist or Q to quit adding: ", this.mediaType);
            input = Constants.keyboard.nextLine();
            if (input.equals("A")) {
                try {
                    switch (this.mediaType) {
                        case "SONG":
                            if (this.songs.size() == 5)
                                throw new PlayListFullException("Playlist %s is full. You cannot add new %s to this playlist.");
                            this.songs.add(new Song());
                            break;
                        case "PODCAST":
                            if (this.podcasts.size() == 5)
                                throw new PlayListFullException("Playlist %s is full. You cannot add new %s to this playlist.");
                            this.podcasts.add(new Podcast());
                            break;
                            case "SHORTCLIP":
                            if (this.shortClips.size() == 5)
                                throw new PlayListFullException("Playlist %s is full. You cannot add new %s to this playlist.");
                            this.shortClips.add(new ShortClip());
                            break;
                    }
                } catch (PlayListFullException e) {
                    System.err.printf(e.getMessage(), this.name, this.mediaType.toLowerCase());
                }
            }
        } while (!input.equals("Q"));
    }

    public Playlist (String[] args) {
        this.name = args[0];
        this.mediaType = args[1];
        this.fileName = args[2];
        this.songs = new ArrayList<>();
        this.podcasts = new ArrayList<>();
        this.shortClips = new ArrayList<>();
    }

    public void addSongs (Song songObject) {
        this.songs.add(songObject);
    }

    public void addPodcasts (Podcast podcastObject) {
        this.podcasts.add(podcastObject);
    }

    public void addShortClips (ShortClip shortClipObject) {
        this.shortClips.add(shortClipObject);
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getMediaType() {
        return this.mediaType;
    }

    public String getName() {
        return this.name;
    }
}