package Pets;

import Animation.DogAnimation;
import javax.swing.*;

public class Dog extends Pet {
    private DogAnimation animationPanel;
    private boolean isMoving = false;
    private boolean isAttacking = false;

    public Dog(String name, JFrame frame) {
        super(name);
        animationPanel = new DogAnimation();
        frame.add(animationPanel);

        // Start with idle animation
        animationPanel.setAnimation(DogAnimation.DogState.IDLE);
    }

    public void walk() {
        isMoving = true;
        animationPanel.setAnimation(DogAnimation.DogState.WALK);
    }

    public void stopWalking() {
        isMoving = false;
        if (!isAttacking) { // Only go back to idle if not attacking
            animationPanel.setAnimation(DogAnimation.DogState.IDLE);
        }
    }

    public void attack() {
        isAttacking = true;
        animationPanel.setAnimation(DogAnimation.DogState.ATTACK);
    }

    public void stopAttacking() {
        isAttacking = false;
        if (isMoving) {
            animationPanel.setAnimation(DogAnimation.DogState.WALK);
        } else {
            animationPanel.setAnimation(DogAnimation.DogState.IDLE);
        }
    }

    public void hurt() {
        animationPanel.setAnimation(DogAnimation.DogState.HURT);
    }

    public void die() {
        animationPanel.setAnimation(DogAnimation.DogState.DEATH);
    }

    // Getters for state
    public boolean isMoving() {
        return isMoving;
    }

    public boolean isAttacking() {
        return isAttacking;
    }
}
