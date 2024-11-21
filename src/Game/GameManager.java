package Game;

import UI.KeyboardListener;
import UI.MainScreen;

import javax.swing.*;
import java.util.Map;

/**
 * The GameManager class is responsible for managing the overall flow of the game,
 * including data retrieval, UI interaction, and screen management.
 */
public class GameManager {

    /**
     * Constructs a new GameManager instance and retrieves data for the pet with ID "1".
     * This is primarily for initialization and testing purposes.
     */
    public GameManager() {
        Map<String, String> petData = DataManager.getPetAttributes("1");
        System.out.println(petData.values());
    }

    /**
     * Starts the game by initializing the main UI screen.
     * This method serves as the entry point for launching the game interface.
     */
    public void start() {
        new MainScreen();
    }

    /**
     * Changes the currently displayed screen in the game UI.
     *
     * @param panel The new JPanel to be displayed. This panel replaces the current
     *              content on the main screen.
     */
    public void changeScreen(JPanel panel) {
        // Implementation for changing screens will go here.
        // This method can be used to update the visible content in the main window.
    }
}
