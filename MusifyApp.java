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
        
        app.displayWelcomeMessage(args);
        app.runMainMenu(args);
    }

    private ArrayList<String[]> readFiles (Scanner fileReader, String objectType) {
        ArrayList<String[]> fileDetailsList = new ArrayList<>();
        while (fileReader.hasNextLine()) {
            try {
                String[] fileDetails = fileReader.nextLine().split(",");
                if (objectType.equals("Playlist")) {
                    if (fileDetails.length != 3)
                        throw new InvalidLineException("Invalid Playlist data. Skipping this line.");
                    if (!Arrays.asList(this.mediaTypes).contains(fileDetails[1]))
                        throw new InvalidFormatException("Incorrect Media Type. Skipping this line.");
                } else if (objectType.equals("Song")) {
                    if (fileDetails.length != 6)
                        throw new InvalidLineException("Song details incomplete. Skipping this line.");
                    if (!Arrays.asList(this.genres).contains(fileDetails[3]))
                        throw new InvalidFormatException("Incorrect Genre for Song. Skipping this line.");
                } else if (objectType.equals("Podcast")) {
                    if (fileDetails.length != 8)
                        throw new InvalidLineException("Podcast details incomplete. Skipping this line.");
                    if (!Arrays.asList(this.categories).contains(fileDetails[3]))
                        throw new InvalidFormatException("Incorrect Category for Podcast. Skipping this line.");
                } else {
                    if (fileDetails.length != 5)
                        throw new InvalidLineException("ShortClip details incomplete. Skipping this line.");
                }
                fileDetailsList.add(fileDetails);
            } catch (InvalidLineException e) {
                System.err.println(e.getMessage());
            } catch (InvalidFormatException e) {
                System.err.println(e.getMessage());
            }
        }
        return fileDetailsList;
                
    }

    @SuppressWarnings("rawtypes")
    private void handleFiles() {
        Scanner fileReader;
        try {
            fileReader = new Scanner(new FileInputStream("data/" + this.playlistListFile));
            for (String[] playlistDetails : this.readFiles(fileReader, "Playlist"))
                this.playlists.add(new Playlist(playlistDetails[0], playlistDetails[1], playlistDetails[2]));
            fileReader.close();

            for (Playlist playlist : this.playlists) {
                fileReader = new Scanner(new FileInputStream("data/playlist/" + playlist.getFileName()));
                // if (playlist.)
                // for (String[] mediaDetails : this.readFiles(fileReader)) {
                // }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            // System.err.println("Invalid or missing file.");
        }
    }

    private void runMainMenu (String[] args) {
        
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

    private void displayWelcomeMessage (String[] args) {
        
    }
}

