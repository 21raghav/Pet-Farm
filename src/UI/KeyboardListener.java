package UI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Pets.Pet;

/**
 * The KeyboardListener class listens for keyboard input and controls the provided Animal.
 */
public class KeyboardListener implements KeyListener {
    private Pet pet;

    public KeyboardListener(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Currently, no actions are performed on keyTyped.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int moveAmount = 5; // Adjust movement speed as needed
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                pet.move(0, -moveAmount); // Move up
                pet.walk();
                break;
            case KeyEvent.VK_A:
                pet.move(-moveAmount, 0); // Move left
                pet.walk();
                break;
            case KeyEvent.VK_S:
                pet.move(0, moveAmount); // Move down
                pet.walk();
                break;
            case KeyEvent.VK_D:
                pet.move(moveAmount, 0); // Move right
                pet.walk();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pet.stopWalking();
    }
}
