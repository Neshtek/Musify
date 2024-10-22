package extra;

import error.MediaNotFoundException;

public interface MediaType {
    public void displayDetails(int index);
    public void play() throws MediaNotFoundException;
    public String toString();
}
