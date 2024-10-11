import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import exception.*;

public class MusifyApp {

    final private String[] mediaTypes = {"SONG", "PODCAST", "SHORTCLIP"};
    final private String[] genres = {"POP", "ROCK", "JAZZ"};
    final private String[] categories = {"HEALTH", "EDUCATION", "TECHNOLOGY"};

    public static void main(String[] args)  {
        MusifyApp app = new MusifyApp();
        
        app.handleFiles(args);
        app.displayWelcomeMessage(args);
        app.runMainMenu(args);
    }

    private void handleFiles(String[] args) {
        String playlistListFile = null;
        if (args.length == 2)
            playlistListFile = args[1];
        if (playlistListFile != null) {
            Scanner playlistListReader = null;
            try {
                playlistListReader = new Scanner(new FileInputStream("./data/" + playlistListFile));
            } catch (IOException e) {
                System.err.println("Invalid or missing file.");
                System.exit(1);
            }
            while (playlistListReader.hasNextLine()) {
                try {
                    String playlistLine = playlistListReader.nextLine();
                    ArrayList<String> playlistLineValues = new ArrayList<>(Arrays.asList(playlistLine.split(",")));
                    if (playlistLineValues.size() != 3) {
                        throw new InvalidLineException("Invalid Playlist data. Skipping this line.");
                    } else {
                        String playlistName = playlistLineValues.get(0);
                        String mediaType = playlistLineValues.get(1);
                        if (!Arrays.asList(mediaTypes).contains(mediaType)) {
                            throw new InvalidFormatException("Incorrect Media Type. Skipping this line.");
                        }
                        String playlistFile = playlistLineValues.get(2);
                        Scanner playlistReader = null;
                        try {
                            playlistReader = new Scanner(new FileInputStream("./data/playlist/" + playlistFile));
                        } catch (IOException e) {
                            System.err.println("Invalid or missing file.");
                        }
                        while (playlistReader.hasNextLine()) {
                            try {
                                String mediaLine = playlistReader.nextLine();
                                ArrayList<String> mediaLineValues = new ArrayList<>(Arrays.asList(mediaLine.split(",")));

                                String mediaName = mediaLineValues.get(0);
                                String mediaDescription = mediaLineValues.get(1);
                                ArrayList<String> mediaCreatorNames = new ArrayList<>(Arrays.asList(mediaLineValues.get(2).split("#")));

                                if (mediaType.equals("SONG")) {
                                    if (mediaLineValues.size() != 6) {
                                        throw new InvalidLineException("Song details incomplete. Skipping this line.");
                                    } else {
                                        String songGenre = mediaLineValues.get(3);
                                        if (!Arrays.asList(genres).contains(songGenre)) {
                                            throw new InvalidFormatException("Incorrect Genre for Song. Skipping this line.");
                                        }
                                        try {
                                            int mediaDuration = Integer.parseInt(mediaLineValues.get(4));
                                        } catch (NumberFormatException e) {
                                            throw new InvalidFormatException("Duration in mins not in correct format. Skipping this line.");
                                        }
                                        String mediaCaptionFile = mediaLineValues.get(5);
                                    }
                                } else if (mediaType.equals("PODCAST")) {
                                    if (mediaLineValues.size() != 8) {
                                        throw new InvalidLineException("Podcast details incomplete. Skipping this line.");
                                    } else {
                                        String podcastCategory = mediaLineValues.get(3);
                                        if (!Arrays.asList(categories).contains(podcastCategory)) {
                                            throw new InvalidFormatException("Incorrect Category for Podcast. Skipping this line.");
                                        }
                                        String podcastSeries = mediaLineValues.get(4);
                                        try {
                                            int podcastEpisode = Integer.parseInt(mediaLineValues.get(5));
                                        } catch (NumberFormatException e) {
                                            throw new InvalidFormatException("Episode number not in correct format. Skipping this line.");
                                        }
                                        try {
                                            int mediaDuration = Integer.parseInt(mediaLineValues.get(6));
                                        } catch (NumberFormatException e) {
                                            throw new InvalidFormatException("Duration in mins not in correct format. Skipping this line.");
                                        }
                                        String mediaCaptionFile = mediaLineValues.get(7);
                                    }
                                } else {
                                    try {
                                        int mediaDuration = Integer.parseInt(mediaLineValues.get(3));
                                    } catch (NumberFormatException e) {
                                        throw new InvalidFormatException("Duration in mins not in correct format. Skipping this line.");
                                    }
                                    String mediaCaptionFile = mediaLineValues.get(4);
                                }
                            } catch (InvalidLineException e) {
                                System.err.println(e.getMessage());
                            } catch (InvalidFormatException e) {
                                System.err.println(e.getMessage());
                            }
                        }
                    }
                } catch (InvalidLineException e) {
                    System.err.println(e.getMessage());
                } catch (InvalidFormatException e) {
                    System.err.println(e.getMessage());
                }
            }
            playlistListReader.close();
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

