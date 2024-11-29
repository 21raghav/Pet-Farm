package Pets;

import Animation.Animation;
import Animation.AnimationState;

import javax.swing.*;
import java.awt.*;

/**
 * The abstract `Pet` class serves as the base class for all pet types in the application.
 * It provides common attributes and methods for managing the state of a pet,
 * including health, sleep level, and hunger level.
 */
public abstract class Pet {

    // Fields for idle animation frames (not yet used)
    private Image[] idleFrames;

    private Boolean isAttacking = false;
    private Boolean isWalking = false;
    private Boolean isMoving = false;

    // Health attributes
    private int currentHealth;
    private int maxHealth;

    private Animation animationPanel;

    // Pet state attributes
    private int sleepLevel;
    private int hungerLevel;

    private int x = 340; // Initial X position
    private int y = 100; // Initial Y position

    private int screenWidth;

    // Name of the pet

    /**
     * Constructs a Pet with a given name and initializes its health to 100.
     *
     * @param name The name of the pet.
     */
    public Pet(String name, Animation animation) {
        this.maxHealth = this.currentHealth = 100; // Initialize health to 100
        this.animationPanel = animation;

        // Get the screen size of the primary monitor
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Get the width and height of the screen
        this.screenWidth = screenSize.width;

    }

    /**
     * Gets the pet's maximum health.
     *
     * @return The pet's maximum health.
     */
    public int getHealth() {
        return maxHealth;
    }

    // Movement method
    public void move(int dx, int dy) {
        if (x+ dx < 0 || x +dx > 675) {
            return;
        }
        x += dx;
        y += dy;
        animationPanel.setLocation(x, 150);
    }

    /**
     * Increases the pet's maximum health by a specified amount.
     *
     * @param health The amount to increase the maximum health.
     */
    public void increaseHealth(int health) {
        this.maxHealth += health;
    }

    /**
     * Gets the pet's current sleep level.
     *
     * @return The pet's sleep level.
     */
    public int getSleepLevel() {
        return sleepLevel;
    }

    /**
     * Sets the pet's sleep level to a specified value.
     *
     * @param sleepLevel The new sleep level.
     */
    public void setSleepLevel(int sleepLevel) {
        this.sleepLevel = sleepLevel;
    }

    /**
     * Gets the pet's current hunger level.
     *
     * @return The pet's hunger level.
     */
    public int getHungerLevel() {
        return hungerLevel;
    }

    /**
     * Sets the pet's hunger level to a specified value.
     *
     * @param hungerLevel The new hunger level.
     */
    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    public void hurt() {
        animationPanel.setAnimation(AnimationState.HURT);
    }

    public void die() {
        animationPanel.setAnimation(AnimationState.DEATH);
    }

    // Getters for state
    public boolean isMoving() {
        return isMoving;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public JPanel getAnimationPanel() {
        return animationPanel.getPanel();
    }

    public void walk() {
        isMoving = true;
        animationPanel.setAnimation(AnimationState.WALK);
    }

    public void stopWalking() {
        isMoving = false;
        if (!isAttacking) { // Only go back to idle if not attacking
            animationPanel.setAnimation(AnimationState.IDLE);
        }
    }

    public void attack() {
        isAttacking = true;
        animationPanel.setAnimation(AnimationState.ATTACK);
    }

    public void stopAttacking() {
        isAttacking = false;
        if (isMoving) {
            animationPanel.setAnimation(AnimationState.WALK);
        } else {
            animationPanel.setAnimation(AnimationState.IDLE);
        }
    }
}
