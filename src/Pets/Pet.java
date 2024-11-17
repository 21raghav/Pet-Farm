package Pets;

import java.awt.*;

public abstract class Pet {

    private Image[] idleFrames;
    private int currentHealth;
    private int maxHealth;
    private int sleepLevel;
    private int hungerLevel;


    private String name;

    public Pet(String name) {
        this.maxHealth = this.currentHealth = 100;
    }

    public int getHealth() {
        return maxHealth;
    }

    public void increaseHealth(int health) {
        this.maxHealth += health;
    }

    public int getSleepLevel() {
        return sleepLevel;
    }

    public void setSleepLevel(int sleepLevel) {
        this.sleepLevel = sleepLevel;
    }

    public int getHungerLevel() {
        return hungerLevel;
    }

    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }
}