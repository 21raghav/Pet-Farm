package Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.*;

public abstract class Animation extends JPanel{
    protected BufferedImage[] idleFrames;
    protected BufferedImage[] attackFrames;
    protected BufferedImage[] deathFrames;
    protected BufferedImage[] hurtFrames;
    protected BufferedImage[] walkFrames;
    private boolean[] framesDone = new boolean[4];

    protected boolean flipHorizontally = false;
    private boolean isLocked = false;

    private BufferedImage[] currentAnimation; // Tracks which animation is playing
    private int currentFrame = 0;
    private ScheduledExecutorService scheduler;

    public void lock() {
        this.isLocked = true;
    }

    public void unlock() {
        this.isLocked = false;
    }

    private int x = 380; // x-coordinate of the Dog
    private int y = 240; // y-coordinate of the Dog

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setLocation(int x, int y) {
        if (!isLocked) {
            flipHorizontally = this.x > x;
            this.x = x;
            this.y = y;
            repaint();  // Repaint the panel with the new position
        }
    }


    private AnimationState currentState = AnimationState.IDLE;

    public Animation() {
        loadAnimations();

        if (idleFrames == null || idleFrames.length == 0) {
            throw new IllegalStateException("Failed to load idle animation frames.");
        }

        currentAnimation = idleFrames;

        // Increase the panel size to allow more space for movement
        int panelWidth = 1600;  // Width of the panel
        int panelHeight = 900;  // Height of the panel
        setPreferredSize(new Dimension(panelWidth, panelHeight));

        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            currentFrame = (currentFrame + 1) % currentAnimation.length;
            repaint(); // Repaint on the EDT
        }, 0, 100, TimeUnit.MILLISECONDS); // Update every 100ms (10 FPS)

        this.setOpaque(false);
        // Start with idle animation
        this.setAnimation(AnimationState.IDLE);
    }


    public void loadAnimations() {
        System.out.println("loadAnimations method must be overrided");
    }

    protected BufferedImage[] loadFrames(String path, int frameCount, int frameWidth, int frameHeight) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("File not found: " + path);
        }
        BufferedImage spriteSheet = ImageIO.read(file);
        BufferedImage[] frames = new BufferedImage[frameCount];

        for (int i = 0; i < frameCount; i++) {
            frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }
        return frames;
    }

    public void setAnimation(AnimationState state) {
        if (!isLocked) {
            this.framesDone = new boolean[4];
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
    }

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

        if (this.currentAnimation == deathFrames) {
            if (!this.framesDone[currentFrame]) {
                this.framesDone[currentFrame] = true;
                g.drawImage(currentAnimation[currentFrame], this.x, this.y, panelWidth, panelHeight, this);
            } else {
                g.drawImage(currentAnimation[3], this.x, this.y, panelWidth, panelHeight, this);
            }

        }
        else {
            if (flipHorizontally) {
                // Flip the image horizontally by using Graphics2D transformation
                Graphics2D g2d = (Graphics2D) g;
                // Apply horizontal flip by scaling with -1 on the X-axis
                g2d.translate(panelWidth, 0);  // Move the origin to the right side of the image
                g2d.scale(-1, 1);  // Flip horizontally
                // Dispose of the Graphics2D object
                // Draw the flipped image

                g2d.drawImage(currentAnimation[currentFrame], -this.x, this.y, panelWidth, panelHeight, this); // Draw the flipped image
                g2d.dispose();  // Dispose of the Graphics2D object
            } else {
                // Draw the current frame of the Dog object, scaled to panel size
                g.drawImage(currentAnimation[currentFrame], this.x, this.y, panelWidth, panelHeight, this);
            }

        }

    }

}
