package Game;

import UI.KeyboardListener;
import UI.MainScreen;

import javax.swing.*;
import java.util.Map;


public class GameManager {
    public GameManager() {
        Map<String, String> petData = (DataManager.getPetAttributes("1"));
        System.out.println(petData.values());

    }
    public void start() {
        new MainScreen();
    }

    public void changeScreen(JPanel panel) {

    }
}