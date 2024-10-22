import util.*;
import extra.Constants;
import error.*;
import java.util.*;
import java.io.*;

public class MusifyApp {
    private final String[] mediaTypes = {"SONG", "PODCAST", "SHORTCLIP"};
    private final String[] genres = {"POP", "ROCK", "JAZZ"};
    private final String[] categories = {"HEALTH", "EDUCATION", "TECHNOLOGY"};

    private String userName;
    private String playlistListFile;

    private ArrayList<Playlist> playlists = new ArrayList<>();

    public static void main (String[] args)  {
        MusifyApp app = new MusifyApp();
        
        if (args.length != 2) {
            app.userName = "Stranger";
            app.playlistListFile = null;
        } else {
            app.userName = args[0];
            app.playlistListFile = args[1];
            try {
                app.handleFiles();
            } catch (IOException e) {
                System.err.println("Invalid or missing file.");
            }
        }
        app.displayWelcomeMessage();

        String menuChoice = "";
        do {
            try {
                menuChoice = app.runMainMenu(menuChoice);
            } catch (EmptyException | MediaNotFoundException | InvalidInputException e) {
                System.err.println(e.getMessage());
            }
        } while (!menuChoice.equals("7"));

        try {
            app.writeFiles();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.printf("Exiting Musify. Goodbye, %s.\n", app.userName);
    }

    private void handleFiles() throws IOException {
        Scanner fileReader = new Scanner(new FileInputStream("data/" + this.playlistListFile));
        while (fileReader.hasNextLine()) {
            try {
                fileReader = this.storePlaylists(fileReader);
            } catch (InvalidFormatException | InvalidLineException e) {
                System.err.println(e.getMessage());
            }
        }
        fileReader.close();

        for (Playlist playlist : this.playlists) {
            try {
                fileReader = new Scanner(new FileInputStream("data/playlist/" + playlist.getFileName()));
                while (fileReader.hasNextLine()) {
                    try {
                        fileReader = this.storeMedia(fileReader, playlist);
                    } catch (InvalidLineException | InvalidFormatException | PlayListFullException e) {
                        System.err.println(e.getMessage());
                    }
                }
                fileReader.close();
            } catch (IOException e) {
                System.err.println("Invalid or missing file.");
            }
        }
    }

    private Scanner storePlaylists (Scanner fileReader) throws InvalidFormatException, InvalidLineException {
        String[] fileDetails;
        fileDetails = fileReader.nextLine().split(",");
        if (fileDetails.length != 3)
            throw new InvalidLineException("Invalid Playlist data. Skipping this line.");
        if (!Arrays.asList(this.mediaTypes).contains(fileDetails[1]))
            throw new InvalidFormatException("Incorrect Media Type. Skipping this line.");
        this.playlists.add(new Playlist(fileDetails));
        return fileReader;
    }

    private Scanner storeMedia (Scanner fileReader, Playlist playlist) throws InvalidFormatException, InvalidLineException, PlayListFullException {
        String[] fileDetails;
        boolean durationFlag = true, episodeFlag = true, genreFlag = false;
        fileDetails = fileReader.nextLine().split(",");
        switch (playlist.getMediaType()) {
            case "SONG":
                if (fileDetails.length != 6)
                    throw new InvalidLineException("Song details incomplete. Skipping this line.");
                genreFlag = false;
                for (String genre : this.genres) {
                    if (genre.equalsIgnoreCase(fileDetails[3])) {
                        genreFlag = true;
                        break;
                    }
                }
                if (!genreFlag)
                    throw new InvalidFormatException("Incorrect Genre for Song. Skipping this line.");
                durationFlag = checkFormat(fileDetails[4]);
                break;
            
            case "PODCAST":
                if (fileDetails.length != 8)
                    throw new InvalidLineException("Podcast details incomplete. Skipping this line.");
                genreFlag = false;
                for (String category : this.categories) {
                    if (category.equalsIgnoreCase(fileDetails[3])) {
                        genreFlag = true;
                        break;
                    }
                }
                if (!genreFlag)
                    throw new InvalidFormatException("Incorrect Category for Podcast. Skipping this line.");
                durationFlag = checkFormat(fileDetails[6]);
                episodeFlag = checkFormat(fileDetails[5]);
                break;
            
            case "SHORTCLIP":
                if (fileDetails.length != 5)
                    throw new InvalidLineException("ShortClip details incomplete. Skipping this line.");
                durationFlag = checkFormat(fileDetails[3]);
                break;
        }
        if (!episodeFlag)
            throw new InvalidFormatException("Episode number not in correct format. Skipping this line.");
        if (!durationFlag)
            throw new InvalidFormatException("Duration in mins not in correct format. Skipping this line.");
        playlist.addMedia(fileDetails);
        return fileReader;
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

    private Playlist matchPlaylistName() throws EmptyException, MediaNotFoundException {
        if (this.playlists.isEmpty())
            throw new EmptyException("No playlists found.");
        System.out.print("Enter Playlist Name: ");
        String playlistName = Constants.keyboard.nextLine();
        for (Playlist playlist : this.playlists) {
            if (playlist.matchName(playlistName)) {
                return playlist;
            }
        }
        throw new MediaNotFoundException("No such playlist found with name: " + playlistName);
    }

    private void displayPlaylists() throws EmptyException {
        if (this.playlists.isEmpty())
            throw new EmptyException("No playlists found.");
        System.out.println("Here are your playlists-");
        System.out.printf(Constants.PLAYLIST_HEADER_FORMATTER, "#", "Type", "Playlist Name");
        System.out.println("-".repeat(46));
        for (int i = 0; i < this.playlists.size(); i++) {
            System.out.printf(Constants.PLAYLIST_FORMATTER, (i + 1), this.playlists.get(i).getMediaType(), this.playlists.get(i).getName());
        }
    }

    private String runMainMenu(String menuChoice) throws EmptyException, MediaNotFoundException, InvalidInputException {
        Playlist playlist = null;
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
                playlist = this.matchPlaylistName();
                playlist.displayMedia();
                break;

            case "4":
                playlist = this.matchPlaylistName();
                this.playlists.remove(playlist);
                System.out.println("Playlist removed successfully.");
                break;
                
            case "5":
                playlist = this.matchPlaylistName();
                playlist.runModifyMenu();
                break;

            case "6":
                playlist = this.matchPlaylistName();
                playlist.play();
                break;
            
            case "7":
                break;

            default:
                throw new InvalidInputException(Constants.INVALID_INPUT);
        }
        return menuChoice;
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
        System.out.printf("Welcome %s. Choose your music, podcasts or watch short clips.", this.userName);
    }

    private void writeFiles() throws IOException {
        PrintWriter output;
        if (this.playlistListFile == null)
            output = new PrintWriter(new FileOutputStream("data/playlists.txt"));
        else
            output = new PrintWriter(new FileOutputStream("data/" + playlistListFile));    
        if (!this.playlists.isEmpty()) {
            for (Playlist playlist : this.playlists) {
                output.println(playlist);
                playlist.writeFiles();
            }
            System.out.println("Playlist data saved.");
        }
        output.close();
    }
}