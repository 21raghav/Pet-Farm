package Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DogAnimation extends JPanel {
    private BufferedImage[] idleFrames;
    private BufferedImage[] attackFrames;
    private BufferedImage[] deathFrames;
    private BufferedImage[] hurtFrames;
    private BufferedImage[] walkFrames;

    private BufferedImage[] currentAnimation; // Tracks which animation is playing
    private int currentFrame = 0;
    private final Timer animationTimer;

    public enum DogState {
        IDLE,
        ATTACK,
        DEATH,
        HURT,
        WALK
    }

    private DogState currentState = DogState.IDLE;

    public DogAnimation() {
        loadAnimations();
        currentAnimation = idleFrames;

        animationTimer = new Timer(100, e -> {
            currentFrame = (currentFrame + 1) % currentAnimation.length;
            repaint();
        });
        animationTimer.start();
    }

    private void loadAnimations() {
        try {
            // Load all animations
            idleFrames = loadFrames(
                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/2 Dog 2/Idle.png", 4);
            attackFrames = loadFrames(
                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/2 Dog 2/Attack.png", 4);
            deathFrames = loadFrames(
                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/2 Dog 2/Death.png", 4);
            hurtFrames = loadFrames(
                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/2 Dog 2/Hurt.png", 4);
            walkFrames = loadFrames(
                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/2 Dog 2/Walk.png", 4);
        } catch (IOException e) {
        }
    }

    private BufferedImage[] loadFrames(String path, int frameCount) throws IOException {
        BufferedImage spriteSheet = ImageIO.read(new File(path));
        BufferedImage[] frames = new BufferedImage[frameCount];

        // Calculate frame width based on sprite sheet width and frame count
        int frameWidth = spriteSheet.getWidth() / frameCount;
        int frameHeight = spriteSheet.getHeight();

        System.out.println("Loading " + path);
        System.out.println("Sprite sheet dimensions: " + spriteSheet.getWidth() + "x" + spriteSheet.getHeight());
        System.out.println("Frame dimensions: " + frameWidth + "x" + frameHeight);

        for (int i = 0; i < frameCount; i++) {
            frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }
        return frames;
    }

    public void setAnimation(DogState state) {
        if (currentState != state) {
            currentState = state;
            currentFrame = 0; // Reset to first frame
            switch (state) {
                case IDLE -> currentAnimation = idleFrames;
                case ATTACK -> currentAnimation = attackFrames;
                case DEATH -> currentAnimation = deathFrames;
                case HURT -> currentAnimation = hurtFrames;
                case WALK -> currentAnimation = walkFrames;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Scaling factor (e.g., 2.0 for double size)
        double scaleFactor = 10.0;

        int scaledWidth = (int) (currentAnimation[currentFrame].getWidth() * scaleFactor);
        int scaledHeight = (int) (currentAnimation[currentFrame].getHeight() * scaleFactor);

        // Draw the current frame of the Dog object
        g.drawImage(currentAnimation[currentFrame], 50, 50, scaledWidth, scaledHeight, this); // Position at (50,50) for
                                                                                              // example
    }
}
