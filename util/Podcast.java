package util;

import extra.*;

public class Podcast extends Media {
    private String category;
    private String seriesName;
    private int episodeNo;

    public Podcast() {
        super();
        String input;
        System.out.print("Add the category: ");
        input = Constants.keyboard.nextLine();
        this.category = input;
        do {
            System.out.print("Enter the host Name or Q to stop entering the host name: ");
            input = Constants.keyboard.nextLine();
            if (!input.equals("Q"))
            super.setCreator(input);
        } while (!input.equals("Q"));
        System.out.print("Enter the series Name: ");
        input = Constants.keyboard.nextLine();
        this.seriesName = input;
        System.out.print("Enter the episode Number: ");
        input = Constants.keyboard.nextLine();
        this.episodeNo = Integer.parseInt(input);
    }

    public Podcast (String[] args) {
        super(args[0], args[1], args[2], args[6], args[7]);
        this.category = args[3];
        this.seriesName = args[4];
        this.episodeNo = Integer.parseInt(args[5]);
    }

    public void displayDetails(int index) {
        System.out.printf(Constants.PODCAST_DATA_FORMATTER, (index + 1), super.getName(), super.getCreators(), super.getDescription(), this.category, this.seriesName, this.episodeNo, super.getDuration());
    }
}
