package Game;

import UI.MainScreen;

public class GameManager {
    public MainScreen mainScreen;

    public void start() {
        System.out.println("Starting game..."); // Debug print
        mainScreen = new MainScreen();
    }
}