import Game.DataManager;
import Game.GameManager;
import Game.GameTimer;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Opens the csv to interpret the game states
 *
 */
public class Main {
    public static void main(String[] args) {
        GameManager manager = new GameManager();
        manager.start();
        new GameTimer();
    }
}