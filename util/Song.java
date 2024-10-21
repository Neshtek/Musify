package util;

import java.util.*;

public class Song extends Media {
    private String genre;

    public Song() {};

    public Song (String[] args) {
        super(args[0], args[1], args[2], args[4], args[5]);
        this.genre = args[3];
    }
}
