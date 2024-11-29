package Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class DogAnimation extends JPanel implements Animation {
    private BufferedImage[] idleFrames;
    private BufferedImage[] attackFrames;
    private BufferedImage[] deathFrames;
    private BufferedImage[] hurtFrames;
    private BufferedImage[] walkFrames;
    private boolean flipHorizontally = false;

    private BufferedImage[] currentAnimation; // Tracks which animation is playing
    private int currentFrame = 0;
    private final Timer animationTimer;

    private int x = 340; // x-coordinate of the Dog
    private int y = 150; // y-coordinate of the Dog

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void switchToIdle() {

    }

    @Override
    public void switchToWalking() {

    }

    @Override
    public void switchToSleeping() {

    }

    @Override
    public void switchToHurt() {

    }


    @Override
    public void setLocation(int x, int y) {

        flipHorizontally = this.x > x;
        this.x = x;
        this.y = y;
        repaint();  // Repaint the panel with the new position
    }


    private AnimationState currentState = AnimationState.IDLE;

    public DogAnimation() {
        loadAnimations();

        if (idleFrames == null || idleFrames.length == 0) {
            throw new IllegalStateException("Failed to load idle animation frames.");
        }

        currentAnimation = idleFrames;
//
//        // Set preferred size based on a desired scale factor
//        double scaleFactor = 4.0; // Adjust as needed
//        int scaledWidth = (int) (idleFrames[0].getWidth() * scaleFactor);
//        int scaledHeight = (int) (idleFrames[0].getHeight() * scaleFactor);
//        setPreferredSize(new Dimension(scaledWidth, scaledHeight));

        // Increase the panel size to allow more space for movement
        int panelWidth = 1600;  // Width of the panel
        int panelHeight = 900;  // Height of the panel
        setPreferredSize(new Dimension(panelWidth, panelHeight));

        animationTimer = new Timer(100, e -> {
            currentFrame = (currentFrame + 1) % currentAnimation.length;
            repaint();
        });

        this.setOpaque(false);
        // Start with idle animation
        this.setAnimation(AnimationState.IDLE);
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
                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/2 Dog 2/Hurt.png", 2);
            walkFrames = loadFrames(
                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/2 Dog 2/Walk.png", 4);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading animation assets: " + e.getMessage(),
                    "Asset Loading Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private BufferedImage[] loadFrames(String path, int frameCount) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File not found: " + path);
        }

        BufferedImage spriteSheet = ImageIO.read(file);
        BufferedImage[] frames = new BufferedImage[frameCount];

        // Calculate frame width based on sprite sheet width and frame count
        int frameWidth = 48;
        int frameHeight = 48;

        for (int i = 0; i < frameCount; i++) {
            frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }
        return frames;
    }

    public void setAnimation(AnimationState state) {
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
            // Fallback to idle if the desired animation is null
            if (currentAnimation == null || currentAnimation.length == 0) {
                currentAnimation = idleFrames;
            }
        }
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (currentAnimation == null || currentAnimation.length == 0) {
            currentAnimation = idleFrames;
        }

        // Get panel size
        int panelWidth = 192;
        int panelHeight = 192;

        if (flipHorizontally) {
            // Flip the image horizontally by using Graphics2D transformation
            Graphics2D g2d = (Graphics2D) g;
            // Apply horizontal flip by scaling with -1 on the X-axis
            g2d.translate(panelWidth, 0);  // Move the origin to the right side of the image
            g2d.scale(-1, 1);  // Flip horizontally
            g2d.drawImage(currentAnimation[currentFrame], -this.x, this.y, panelWidth, panelHeight, this); // Draw the flipped image
            g2d.dispose();  // Dispose of the Graphics2D object
        } else {
            // Draw the current frame of the Dog object, scaled to panel size
            g.drawImage(currentAnimation[currentFrame], this.x, this.y, panelWidth, panelHeight, this);
        }
    }
}
