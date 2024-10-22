package util;

import error.MediaNotFoundException;
import extra.*;

public class Song extends Media {
    private String genre;

    public Song() {
        super();
        String input;
        System.out.print("Add the Genre: ");
        input = Constants.keyboard.nextLine();
        this.genre = input.toUpperCase();
        do {
            System.out.print("Enter the artist Name or Q to stop entering the artist name: ");
            input = Constants.keyboard.nextLine();
            if (!input.toUpperCase().equals("Q"))
                super.setCreator(input);
        } while (!input.toUpperCase().equals("Q"));
    }

    public Song (String[] args) {
        super(args[0], args[1], args[2], args[4], args[5]);
        this.genre = args[3];
    }

    public void displayDetails(int index) {
        System.out.printf(Constants.SONG_PLAYLIST_DATA_FORMATTER, (index + 1), super.getName(), super.getCreators(), super.getDescription(), this.genre, super.getDuration());
    }

    public void play() throws MediaNotFoundException {
        System.out.printf("Playing Song: %s by %s for %d mins.", super.getName(), super.getCreators(), super.getDuration());
        if (super.getCaptions().equals(""))
            throw new MediaNotFoundException("Cannot show lyrics. Media not found.");
        else {
            System.out.println("Here are the lyrics to sing along.");
            System.out.print(super.getCaptions());
        }
    }

    public String toString() {
        return String.join(",", super.getName(), super.getDescription(), super.getCreatorsForFile(), this.genre, Integer.toString(super.getDuration()), super.getCaptionFile());
    }
}
