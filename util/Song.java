package util;

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
            if (!input.equals("Q"))
                super.setCreator(input);
        } while (!input.equals("Q"));
    }

    public Song (String[] args) {
        super(args[0], args[1], args[2], args[4], args[5]);
        this.genre = args[3];
    }
}
