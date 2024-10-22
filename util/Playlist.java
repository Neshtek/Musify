package util;

import java.util.*;
import java.io.*;
import error.*;
import extra.*;

public class Playlist {
    private String name;
    private String mediaType;
    private String fileName;
    private ArrayList<Song> songs = new ArrayList<>();
    private ArrayList<Podcast> podcasts = new ArrayList<>();
    private ArrayList<ShortClip> shortClips = new ArrayList<>();

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
        System.out.printf("Add some %s to your Playlist.\n", this.mediaType.toUpperCase());
        do {
            try {
                System.out.printf("Enter A to add a %s to the playlist or Q to quit adding: ", this.mediaType.toUpperCase());
                input = Constants.keyboard.nextLine();
                if (input.equalsIgnoreCase("A")) {
                    try {
                        this.addMedia();
                    } catch (PlayListFullException e) {
                        System.err.printf(e.getMessage(), this.name, this.mediaType.toLowerCase());
                    }
                } else if (!input.equalsIgnoreCase("Q")) {
                    throw new InvalidInputException(Constants.INVALID_INPUT);
                }
            } catch (InvalidInputException e) {
                System.err.println(e.getMessage());
            }
        } while (!input.equalsIgnoreCase("Q"));
    }

    public Playlist (String[] args) {
        this.name = args[0];
        this.mediaType = args[1];
        this.fileName = args[2];
    }

    public void addMedia (String[] fileDetails) throws PlayListFullException {
        switch (this.mediaType) {
            case "SONG":
                if (this.songs.size() == 5)
                    throw new PlayListFullException("Playlist " + this.name + " is full. You cannot add new song to this playlist.");
                this.songs.add(new Song(fileDetails));
                break;
            case "PODCAST":
                if (this.podcasts.size() == 5)
                    throw new PlayListFullException("Playlist " + this.name + " is full. You cannot add new podcast to this playlist.");
                this.podcasts.add(new Podcast(fileDetails));
                break;
            case "SHORTCLIP":
                if (this.shortClips.size() == 5)
                    throw new PlayListFullException("Playlist " + this.name + " is full. You cannot add new shortclip to this playlist.");
                this.shortClips.add(new ShortClip(fileDetails));
                break;
        }
    }

    private void addMedia() throws PlayListFullException {
        switch (this.mediaType) {
            case "SONG":
                if (this.songs.size() == 5)
                    throw new PlayListFullException("Playlist " + this.name + " is full. You cannot add new song to this playlist.");
                this.songs.add(new Song());
                break;
            case "PODCAST":
                if (this.podcasts.size() == 5)
                    throw new PlayListFullException("Playlist " + this.name + " is full. You cannot add new podcast to this playlist.");
                this.podcasts.add(new Podcast());
                break;
                case "SHORTCLIP":
                if (this.shortClips.size() == 5)
                    throw new PlayListFullException("Playlist " + this.name + " is full. You cannot add new shortclip to this playlist.");
                this.shortClips.add(new ShortClip());
                break;
        }
    }

    private void removeMedia() throws MediaNotFoundException {
        if (this.songs.isEmpty() && this.podcasts.isEmpty() && this.shortClips.isEmpty())
            throw new MediaNotFoundException("You can not remove media from an empty list.");
        System.out.printf("Enter the %s to remove: ", this.mediaType.toLowerCase());
        String mediaName = Constants.keyboard.nextLine();
        switch (this.mediaType) {
            case "SONG":
                for (Song song : this.songs)
                    if (song.getName().equalsIgnoreCase(mediaName)) {
                        this.songs.remove(song);
                        System.out.println("Media removed successfully.");
                        break;
                    }
                break;
            case "PODCAST":
                for (Podcast podcast : this.podcasts)
                    if (podcast.getName().equalsIgnoreCase(mediaName)) {
                        this.podcasts.remove(podcast);
                        System.out.println("Media removed successfully.");
                        break;
                    }
                break;
            case "SHORTCLIP":
                for (ShortClip shortClip : this.shortClips)
                    if (shortClip.getName().equalsIgnoreCase(mediaName)) {
                        this.shortClips.remove(shortClip);
                        System.out.println("Media removed successfully.");
                        break;
                    }
                break;
        }
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

    public void play() throws MediaNotFoundException {
        boolean mediaExists = true;
        switch (this.mediaType) {
            case "SONG":
                if (this.songs.isEmpty()) {
                    mediaExists = false;
                    break;
                }
                for (Song song : this.songs) {
                    System.out.println("-".repeat(83));
                    try {
                        song.play();
                    } catch (MediaNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    System.out.println("-".repeat(83));
                }        
                break;
                
            case "PODCAST":
                if (this.podcasts.isEmpty()) {
                    mediaExists = false;
                    break;
                }
                for (Podcast podcast : this.podcasts) {
                    System.out.println("-".repeat(83));
                    try {
                        podcast.play();
                    } catch (MediaNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    System.out.println("-".repeat(83));
                }        
                break;
                
            case "SHORTCLIP":
                if (this.shortClips.isEmpty()) {
                    mediaExists = false;
                    break;
                }
                for (ShortClip shortClip : this.shortClips) {
                    System.out.println("-".repeat(83));
                    try {
                        shortClip.play();
                    } catch (MediaNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    System.out.println("-".repeat(83));
                }        
                break;
        }
        if (!mediaExists)
            throw new MediaNotFoundException("No " + this.mediaType.toLowerCase() + " in the playlist to play.");

    }

    public void displayMedia() throws MediaNotFoundException {
        boolean mediaExists = true;
        switch (this.mediaType) {
            case "SONG":
                if (this.songs.isEmpty()) {
                    mediaExists = false;
                    break;
                }
                System.out.printf(Constants.SONG_PLAYLIST_HEADER, "Id", "Title", "Artist Name", "Description", "Genre", "Duration In Mins");
                System.out.println("-".repeat(125));
                for (int i = 0; i < this.songs.size(); i++)
                    this.songs.get(i).displayDetails(i);
                break;
                
            case "PODCAST":
                if (this.podcasts.isEmpty()) {
                    mediaExists = false;
                    break;
                }
                System.out.printf(Constants.PODCAST_PLAYLIST_HEADER, "Id", "Title", "Host Name(s)", "Description", "Category", "Series Name", "Episode#", "Duration In Mins");
                System.out.println("-".repeat(160));
                for (int i = 0; i < this.podcasts.size(); i++)
                    this.podcasts.get(i).displayDetails(i);
                break;
                
            case "SHORTCLIP":
                if (this.shortClips.isEmpty()) {
                    mediaExists = false;
                    break;
                }
                System.out.printf(Constants.SHORTCLIP_PLAYLIST_HEADER, "Id", "Title", "Artist Name", "Description", "Duration In Mins");
                System.out.println("-".repeat(114));
                for (int i = 0; i < this.shortClips.size(); i++)
                    this.shortClips.get(i).displayDetails(i);
                break;
        }
        if (!mediaExists)
            throw new MediaNotFoundException("No " + this.mediaType.toLowerCase() + " in the playlist to view.");
    }

    public boolean matchName (String name) {
        if (this.name.equalsIgnoreCase(name.toLowerCase()))
            return true;
        else
            return false;
    }

    public void runModifyMenu() {
        String modyifyChoice;
        do {
            this.printModifyMenu();
            modyifyChoice = Constants.keyboard.nextLine();
            switch (modyifyChoice) {
                case "1":
                    try {
                        this.displayMedia();
                    } catch (MediaNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
    
                case "2":
                    try {
                        this.addMedia();
                    } catch (PlayListFullException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                
                case "3":
                    try {
                        this.removeMedia();
                    } catch (MediaNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
            }
        } while (!modyifyChoice.equals("4"));
    }

    private void printModifyMenu() {
        System.out.println("Please select one of the options.");
        System.out.println("1. View the playlist.");
        System.out.printf("2. Add a new %s.\n", this.mediaType.toLowerCase());
        System.out.printf("3. Remove a %s.\n", this.mediaType.toLowerCase());
        System.out.println("4. Exit and go back to main menu.");
    }

    public void writeFiles() throws IOException {
        PrintWriter output = new PrintWriter(new FileOutputStream("data/playlist/" + fileName));
        if (this.mediaType.equalsIgnoreCase("SONG"))
            for (Song song : this.songs)
                output.println(song);
        else if (this.mediaType.equalsIgnoreCase("PODCAST"))
            for (Podcast podcast : this.podcasts)
               output.println(podcast);
        else
            for (ShortClip shortClip : this.shortClips)
                output.println(shortClip);
        output.close();
    }

    public String toString() {
        return String.join(",", this.name, this.mediaType, this.fileName);
    }
}