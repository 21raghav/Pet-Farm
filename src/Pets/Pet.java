package Pets;

import Animation.Animation;
import Animation.AnimationState;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Pet {
    private String name; // Store the pet's name
    private int x = 340; // Initial X position
    private int y = 100; // Initial Y position

    private int currentHealth = 100;
    private int maxHealth = 100;
    private int hungerLevel = 50;
    private int sleepLevel = 50;

    private Animation animationPanel;
    private Map<String, Integer> inventory = new HashMap<>(); // Store inventory items

    public Pet(String name, Animation animation) {
        this.name = name; // Store the pet's name
        this.animationPanel = animation;
    }

    // Getters and setters for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Position
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Health, hunger, sleep
    public int getHealth() {
        return currentHealth;
    }

    public void setHealth(int health) {
        this.currentHealth = Math.min(health, maxHealth);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void increaseHealth(int health) {
        this.currentHealth = Math.min(this.currentHealth + health, this.maxHealth);
    }

    public int getHungerLevel() {
        return hungerLevel;
    }

    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    public int getSleepLevel() {
        return sleepLevel;
    }

    public void setSleepLevel(int sleepLevel) {
        this.sleepLevel = sleepLevel;
    }

    // Inventory
    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    // Retrieve attributes for saving
    public Map<String, String> getAttributes() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("name", name); // Include the name in the attributes
        attributes.put("x", String.valueOf(x));
        attributes.put("y", String.valueOf(y));
        attributes.put("health", String.valueOf(currentHealth));
        attributes.put("maxHealth", String.valueOf(maxHealth));
        attributes.put("hunger", String.valueOf(hungerLevel));
        attributes.put("sleep", String.valueOf(sleepLevel));

        // Add inventory items to attributes
        for (Map.Entry<String, Integer> item : inventory.entrySet()) {
            attributes.put("inventory_" + item.getKey(), String.valueOf(item.getValue()));
        }

        return attributes;
    }

    // Load attributes from a map
    public void setAttributes(Map<String, String> attributes) {
        if (attributes.containsKey("name")) this.name = attributes.get("name");
        if (attributes.containsKey("x")) this.x = Integer.parseInt(attributes.get("x"));
        if (attributes.containsKey("y")) this.y = Integer.parseInt(attributes.get("y"));
        if (attributes.containsKey("health")) this.currentHealth = Integer.parseInt(attributes.get("health"));
        if (attributes.containsKey("maxHealth")) this.maxHealth = Integer.parseInt(attributes.get("maxHealth"));
        if (attributes.containsKey("hunger")) this.hungerLevel = Integer.parseInt(attributes.get("hunger"));
        if (attributes.containsKey("sleep")) this.sleepLevel = Integer.parseInt(attributes.get("sleep"));

        // Load inventory items
        inventory.clear();
        for (String key : attributes.keySet()) {
            if (key.startsWith("inventory_")) {
                String itemName = key.substring("inventory_".length());
                inventory.put(itemName, Integer.parseInt(attributes.get(key)));
            }
        }
    }

    public JPanel getAnimationPanel() {
        return animationPanel.getPanel();
    }

    // Animation methods
    public void walk() {
        animationPanel.setAnimation(AnimationState.WALK);
    }

    public void stopWalking() {
        animationPanel.setAnimation(AnimationState.IDLE);
    }

    public void attack() {
        animationPanel.setAnimation(AnimationState.ATTACK);
    }

    public void stopAttacking() {
        animationPanel.setAnimation(AnimationState.IDLE);
    }

    public void hurt() {
        animationPanel.setAnimation(AnimationState.HURT);
    }

    public void die() {
        animationPanel.setAnimation(AnimationState.DEATH);
    }

    public void move(int dx, int dy) {
        if (x + dx < 0 || x + dx > 675) return;
        x += dx;
        y += dy;
        animationPanel.setLocation(x, 150);
    }
}
