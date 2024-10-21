import util.*;
import extra.*;
import error.*;
import java.util.*;
import java.io.*;

public class MusifyApp {
    private String[] mediaTypes = {"SONG", "PODCAST", "SHORTCLIP"};
    private String[] genres = {"POP", "ROCK", "JAZZ"};
    private String[] categories = {"HEALTH", "EDUCATION", "TECHNOLOGY"};
    private String userName;
    private String playlistListFile;

    public static void main(String[] args)  {
        MusifyApp app = new MusifyApp();
        
        if (args.length != 2) {
            app.userName = "Stranger";
            app.playlistListFile = null;
        } else {
            app.userName = args[0];
            app.playlistListFile = args[1];
            app.handleFiles(args);
        }
        
        app.displayWelcomeMessage(args);
        app.runMainMenu(args);
    }

    private void handleFiles(String[] args) {
        try {
            Scanner fileReader = new Scanner(new FileInputStream("./data/" + this.playlistListFile));
        } catch (IOException e) {
            
        }
    }

    private void runMainMenu(String[] args) {
        
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

    private void displayWelcomeMessage(String[] args) {
        
    }
}

