package UI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The KeyboardListener class listens for keyboard input and performs actions
 * based on specific key presses. It implements the {@link KeyListener} interface
 * and is designed to handle directional inputs (`W`, `A`, `S`, `D`).
 */
public class KeyboardListener implements KeyListener {
    private GameMenu gameMenu;

    public KeyboardListener() {
        this.gameMenu = gameMenu;
    }

    /**
     * Invoked when a key is typed (pressed and released).
     *
     * @param e The {@link KeyEvent} associated with the key typed.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Currently, no actions are performed on keyTyped.
    }

    /**
     * Invoked when a key is pressed. This method listens for directional keys
     * (`W`, `A`, `S`, `D`) and prints a message when one of these keys is pressed.
     *
     * @param e The {@link KeyEvent} associated with the key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                System.out.println("UP");
                break;
            case KeyEvent.VK_A:
                System.out.println("LEFT");
                break;
            case KeyEvent.VK_S:
                System.out.println("DOWN");
                break;
            case KeyEvent.VK_D:
                System.out.println("RIGHT");
                break;
        }
    }

    /**
     * Invoked when a key is released.
     *
     * @param e The {@link KeyEvent} associated with the key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Currently, no actions are performed on keyReleased.
    }
}
