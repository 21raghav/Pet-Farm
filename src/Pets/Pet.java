package Pets;

import java.awt.*;

/**
 * The abstract `Pet` class serves as the base class for all pet types in the application.
 * It provides common attributes and methods for managing the state of a pet,
 * including health, sleep level, and hunger level.
 */
public abstract class Pet {

    // Fields for idle animation frames (not yet used)
    private Image[] idleFrames;

    // Health attributes
    private int currentHealth;
    private int maxHealth;

    // Pet state attributes
    private int sleepLevel;
    private int hungerLevel;

    // Name of the pet
    private String name;

    /**
     * Constructs a Pet with a given name and initializes its health to 100.
     *
     * @param name The name of the pet.
     */
    public Pet(String name) {
        this.name = name;
        this.maxHealth = this.currentHealth = 100; // Initialize health to 100
    }

    /**
     * Gets the pet's maximum health.
     *
     * @return The pet's maximum health.
     */
    public int getHealth() {
        return maxHealth;
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
}
