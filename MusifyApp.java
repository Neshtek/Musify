import util.*;
import extra.*;
import error.*;
import java.util.*;
import java.io.*;

public class MusifyApp {
    private final String[] mediaTypes = {"SONG", "PODCAST", "SHORTCLIP"};
    private final String[] genres = {"POP", "ROCK", "JAZZ"};
    private final String[] categories = {"HEALTH", "EDUCATION", "TECHNOLOGY"};

    private String userName;
    private String playlistListFile;

    private ArrayList<Playlist> playlists;

    public static void main (String[] args)  {
        MusifyApp app = new MusifyApp();
        
        if (args.length != 2) {
            app.userName = "Stranger";
            app.playlistListFile = null;
        } else {
            app.userName = args[0];
            app.playlistListFile = args[1];
            app.handleFiles();
        }
        app.displayWelcomeMessage();
        app.runMainMenu();
    }

    private void storePlaylists (Scanner fileReader) {
        String[] fileDetails;
        while (fileReader.hasNextLine()) {
            try {
                fileDetails = fileReader.nextLine().split(",");
                if (fileDetails.length != 3)
                    throw new InvalidLineException("Invalid Playlist data. Skipping this line.");
                if (!Arrays.asList(this.mediaTypes).contains(fileDetails[1]))
                    throw new InvalidFormatException("Incorrect Media Type. Skipping this line.");
                this.playlists.add(new Playlist(fileDetails));
            } catch (InvalidLineException e) {
                System.err.println(e.getMessage());
            } catch (InvalidFormatException e) {
                System.err.println(e.getMessage());
            }
        }       
    }

    private void storeMedia (Scanner fileReader, Playlist playlist) {
        String[] fileDetails;
        String captionFile = null;
        String duration = null;
        boolean flag;
        while (fileReader.hasNextLine()) {
            try {
                fileDetails = fileReader.nextLine().split(",");
                if (playlist.getMediaType().equals("SONG")) {
                    if (fileDetails.length != 6)
                        throw new InvalidLineException("Song details incomplete. Skipping this line.");
                    if (!Arrays.asList(this.genres).contains(fileDetails[3]))
                        throw new InvalidFormatException("Incorrect Genre for Song. Skipping this line.");
                    flag = checkFormat(fileDetails[4]);
                    if (!flag)
                        throw new InvalidFormatException("Duration in mins not in correct format. Skipping this line.");
                    captionFile = fileDetails[5];
                    playlist.addSongs(new Song(fileDetails));                    
                } else if (playlist.getMediaType().equals("PODCAST")) {
                    if (fileDetails.length != 8)
                        throw new InvalidLineException("Podcast details incomplete. Skipping this line.");
                    if (!Arrays.asList(this.categories).contains(fileDetails[3]))
                        throw new InvalidFormatException("Incorrect Category for Podcast. Skipping this line.");
                    flag = checkFormat(fileDetails[6]);
                    if (!flag)
                        throw new InvalidFormatException("Duration in mins not in correct format. Skipping this line.");
                    flag = checkFormat(fileDetails[5]);
                    if (!flag)
                        throw new InvalidFormatException("Episode number not in correct format. Skipping this line.");
                    captionFile = fileDetails[7];                  
                    playlist.addPodcasts(new Podcast(fileDetails));                    
                } else {
                    if (fileDetails.length != 5)
                        throw new InvalidLineException("ShortClip details incomplete. Skipping this line.");
                    flag = checkFormat(fileDetails[3]);
                    if (!flag)
                        throw new InvalidFormatException("Duration in mins not in correct format. Skipping this line.");
                    captionFile = fileDetails[4]; 
                    playlist.addShortClips(new ShortClip(fileDetails));                    
                }
            } catch (InvalidLineException e) {
                System.err.println(e.getMessage());
            } catch (InvalidFormatException e) {
                System.err.println(e.getMessage());
            }
            Media.checkCaptionExists(captionFile);
        }
    }

    private boolean checkFormat (String stringToCheck) {
        try {
            int value = Integer.parseInt(stringToCheck);
            if (value <= 0)
                return false;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void displayPlaylists() {
        System.out.println("Here are your playlists-");
        System.out.printf(Constants.PLAYLIST_HEADER_FORMATTER, "#", "Type", "Playlist Name");
        for (int i = 0; i < this.playlists.size(); i++) {
            System.out.printf(Constants.PLAYLIST_FORMATTER, (i + 1), this.playlists.get(i).getMediaType(), this.playlists.get(i).getName());
        }
    }

    private void handleFiles() {
        Scanner fileReader;
        try {
            fileReader = new Scanner(new FileInputStream("data/" + this.playlistListFile));
            this.storePlaylists(fileReader);
            fileReader.close();

            for (Playlist playlist : this.playlists) {
                try {
                    fileReader = new Scanner(new FileInputStream("data/playlist/" + playlist.getFileName()));
                    this.storeMedia(fileReader, playlist);
                    fileReader.close();
                } catch (IOException e) {
                    System.err.println("Invalid or missing file.");
                }
            }
        } catch (IOException e) {
            System.err.println("Invalid or missing file.");
        }
    }

    private void runMainMenu() {
        String menuChoice;
        String playlistName;
        do {
            this.printMainMenu();
            menuChoice = Constants.keyboard.nextLine();
            switch (menuChoice) {
                case "1":
                    this.playlists.add(new Playlist());
                    break;
    
                case "2":
                    this.displayPlaylists();
                    break;

                case "3":
                    System.out.print("Enter Playlist Name: ");
                    playlistName = Constants.keyboard.nextLine();
                    for (Playlist playlist : this.playlists) {
                        if (playlist.matchName(playlistName)) {
                            playlist.displayMedia();
                        }
                    }
                    break;

                case "4":
                    System.out.print("Enter Playlist Name: ");
                    playlistName = Constants.keyboard.nextLine();
                    for (Playlist playlist : this.playlists) {
                        if (playlist.matchName(playlistName)) {
                            this.playlists.remove(playlist);
                        }
                    }
                    break;

                case "6":
                    
                    break;

            }
        } while (!menuChoice.equals("7"));
    }

    private void printMainMenu() {
        System.out.println("Please select one of the options.");
        System.out.println("1. Create a new playlist.");
        System.out.println("2. View all playlist.");
        System.out.println("3. View contents of a playlist.");
        System.out.println("4. Remove a playlist.");
        System.out.println("5. Modify a playlist.");
        System.out.println("6. Play contents of a playlist.");
        System.out.println("7. Exit Musify.");
    }

    private void displayWelcomeMessage() {
        if (this.playlistListFile == null)
            System.out.println("No Playlist data found to load.");
        else
            System.out.println("Data loading complete.");
        System.out.printf("Welcome %s. Choose your music, podcasts or watch short clips.Please select one of the options.\n", this.userName);
    }
}

