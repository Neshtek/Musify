import util.*;
import extra.Constants;
import error.*;
import java.util.*;
import java.io.*;

/**
 * This class is the backbone of the entire MusifyApp and houses the main method.
 * @version ver 1.0
 * @author Neelaksh Tayal 1627659
 */
public class MusifyApp {
    // final constant values that were known to us.
    private final String[] mediaTypes = {"SONG", "PODCAST", "SHORTCLIP"};
    private final String[] genres = {"POP", "ROCK", "JAZZ"};
    private final String[] categories = {"HEALTH", "EDUCATION", "TECHNOLOGY"};

    // data extracted from the command line arguments.
    private String userName;
    private String playlistListFile;

    // ArrayList to store all the playlists.
    private ArrayList<Playlist> playlists = new ArrayList<>();

    /**
     * The main method where this music app starts.
     * @param args command line arguments as a String array.
     */
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

    /**
     * This method mainly handles the reading of files and storing of the data they contain.
     * @throws IOException when file is not found or can't be read/written to.
     */
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

    /**
     * Method to store the data about the list of playlists.
     * @param fileReader Scanner object that has the file open.
     * @return Scanner object back to the call point so the updated state circulates accurately.
     * @throws InvalidFormatException when provided data format does not match expected format.
     * @throws InvalidLineException when the file line contains insufficient/extra data points.
     */
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

    /**
     * Method to store the data about the media playlists according to media type.
     * @param fileReader Scanner object that has the file open.
     * @param playlist The current Playlist object we are making changes in.
     * @return Scanner object back to the call point so the updated state circulates accurately.
     * @throws InvalidFormatException when provided data format does not match expected format.
     * @throws InvalidLineException when the file line contains insufficient/extra data points.
     * @throws PlayListFullException when the playlist already contains 5 elements and cant store anymore.
     */
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

    /**
     * Checks if the numerical entries conform to the expected formats.
     * @param stringToCheck the string value of the numerical data extracted from the file.
     * @return a boolean value based on the outcome of the checks.
     */
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

    /**
     * Method to search for a playlist based on user entered name from the list of playlists.
     * @return the found Playlist object.
     * @throws EmptyException when the list of playlists is empty.
     * @throws MediaNotFoundException if matching entry not found in the list.
     */
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

    /**
     * Method to display all playlists for the user.
     * @throws EmptyException if list of playlists is empty.
     */
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

    /**
     * Method that facilitates the functioning of the main menu of the app.
     * @param menuChoice the menu item choice made by the user.
     * @return the menu item choice in order to keep consistency between fields.
     * @throws EmptyException if list of playlists is empty.
     * @throws MediaNotFoundException if the playlist entered by user is not found.
     * @throws InvalidInputException if the wrong input was given for menu item choice.
     */
    private String runMainMenu(String menuChoice) throws EmptyException, MediaNotFoundException, InvalidInputException {
        Playlist playlist = null;
        this.printMainMenu();
        menuChoice = Constants.keyboard.nextLine();
        switch (menuChoice) {
            // create a new playlist
            case "1":
                this.playlists.add(new Playlist());
                break;
            // display all playlists
            case "2":
                this.displayPlaylists();
                break;
            // display contents of a specific playlist
            case "3":
                playlist = this.matchPlaylistName();
                playlist.displayMedia();
                break;
            // delete a playlist
            case "4":
                playlist = this.matchPlaylistName();
                this.playlists.remove(playlist);
                System.out.println("Playlist removed successfully.");
                break;
            // modify a playlist
            case "5":
                playlist = this.matchPlaylistName();
                playlist.runModifyMenu();
                break;
            // play the contents of a playlist
            case "6":
                playlist = this.matchPlaylistName();
                playlist.play();
                break;
            // exit
            case "7":
                break;

            default:
                throw new InvalidInputException(Constants.INVALID_INPUT);
        }
        return menuChoice;
    }

    /**
     * Method that contains the main menu text.
     */
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

    /**
     * Method to display the welcome message for the user.
     */
    private void displayWelcomeMessage() {
        if (this.playlistListFile == null)
            System.out.println("No Playlist data found to load.");
        else
            System.out.println("Data loading complete.");
        System.out.printf("Welcome %s. Choose your music, podcasts or watch short clips.", this.userName);
    }

    /**
     * Method to write the final data into files.
     * @throws IOException if file path specified cant be opened or written to.
     */
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