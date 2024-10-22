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

    public void displayMedia() {
        boolean mediaExists = true;
        switch (this.mediaType) {
            case "SONG":
                if (this.songs.size() != 0) {
                    System.out.printf(Constants.SONG_PLAYLIST_HEADER, "Id", "Title", "Artist Name", "Description", "Genre", "Duration In Mins");
                    for (int i = 0; i < this.songs.size(); i++)
                        this.songs.get(i).displayDetails(i);
                } else
                    mediaExists = false;
                break;
            
            case "PODCAST":
                if (this.podcasts.size() != 0) {
                    System.out.printf(Constants.PODCAST_PLAYLIST_HEADER, "Id", "Title", "Host Name(s)", "Description", "Category", "Series Name", "Episode#", "Duration In Mins");
                    for (int i = 0; i < this.podcasts.size(); i++)
                        this.podcasts.get(i).displayDetails(i);
                } else
                    mediaExists = false;
                break;

            case "SHORTCLIP":
                if (this.shortClips.size() != 0) {
                    System.out.printf(Constants.SHORTCLIP_PLAYLIST_HEADER, "Id", "Title", "Artist Name", "Description", "Duration In Mins");
                    for (int i = 0; i < this.shortClips.size(); i++)
                        this.shortClips.get(i).displayDetails(i);
                } else
                    mediaExists = false;
                break;
        }
        if (!mediaExists)
            System.out.printf("No %s in the playlist to view.\n", this.mediaType.toLowerCase());
    }

    public boolean matchName (String name) {
        if (this.name.toLowerCase().equals(name.toLowerCase()))
            return true;
        else
            return false;
    }
}