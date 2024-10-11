public class MusifyApp {

    public static void main(String[] args)  {
        MusifyApp app = new MusifyApp();
        
        app.handleFiles(args);
        app.displayWelcomeMessage(args);
        app.runMainMenu(args);
    }

    
    private void handleFiles(String[] args) {
        
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

