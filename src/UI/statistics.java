package UI;

import java.awt.*;

public class statistics {
    private int health;
    private int happiness;
    private int hunger;
    private int sleep;
    private final Image statBarImage;
    private final Image healthIcon;

    public statistics(int health, int happiness, int hunger, int sleep, Image statBarImage, Image healthIcon) {
        this.health = health;
        this.happiness = happiness;
        this.hunger = hunger;
        this.sleep = sleep;
        this.statBarImage = statBarImage;
        this.healthIcon = healthIcon;
    }

    public void updateStat(int statIndex, int increment) {
        switch (statIndex) {
            case 0: // Health
                setHealth(Math.min(100, Math.max(0, health + increment)));
                break;
            case 1: // Happiness
                setHappiness(Math.min(100, Math.max(0, happiness + increment)));
                break;
            case 2: // Hunger
                setHunger(Math.min(100, Math.max(0, hunger + increment)));
                break;
            case 3: // Sleep
                setSleep(Math.min(100, Math.max(0, sleep + increment)));
                break;
            default:
                throw new IllegalArgumentException("Invalid stat index: " + statIndex);
        }  
    }

    public void drawStats(Graphics g, int width, int height) {
        int statBarWidth = statBarImage.getWidth(null);
        int statBarHeight = statBarImage.getHeight(null);
        int statBarX = width - statBarWidth - 60;
        int statBarY = 10;

        drawStatBar(g, statBarX, statBarY, getHealth(), Color.RED);
        g.drawImage(healthIcon, statBarX + statBarWidth + 5, statBarY, null);

        statBarY += statBarHeight + 10;
        drawStatBar(g, statBarX, statBarY, getHappiness(), Color.YELLOW);

        statBarY += statBarHeight + 10;
        drawStatBar(g, statBarX, statBarY, getHunger(), Color.GREEN);

        statBarY += statBarHeight + 10;
        drawStatBar(g, statBarX, statBarY, getSleep(), Color.BLUE);
    }

    public void setHealth(int health) {
        this.health =  health; //Math.min(100, Math.max(0, health)); Ensure health is within bounds
    }

    private int getHealth(){
        return health;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness; // Ensure health is within bounds
    }

    private int getHappiness(){
        return happiness;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger; // Ensure health is within bounds
    }

    private int getHunger(){
        return hunger;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep; // Ensure health is within bounds
    }

    private int getSleep(){
        return sleep;
    }

    private void drawStatBar(Graphics g, int x, int y, int percentage, Color color) {
        int width = (int)(statBarImage.getWidth(null) * (percentage / 100.0)) - 4;
        g.drawImage(statBarImage, x, y, null);
        g.setColor(color);
        g.fillRect(x + 2, y + 2, width, statBarImage.getHeight(null) - 4);
    }
}
