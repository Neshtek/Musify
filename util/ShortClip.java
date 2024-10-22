package util;

import extra.*;

public class ShortClip extends Media {
    public ShortClip() {
        super();
        System.out.print("Enter the artist Name: ");
        String input = Constants.keyboard.nextLine();
        super.setCreator(input);
    }

    public ShortClip (String[] args) {
        super(args[0], args[1], args[2], args[3], args[4]);
    }

    public void displayDetails(int index) {
        System.out.printf(Constants.SHORTCLIP_DATA_FORMATTER, (index + 1), super.getName(), super.getCreators(), super.getDescription(), super.getDuration());
    }
}
