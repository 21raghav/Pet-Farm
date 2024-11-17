package Animation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DogAnimation extends JPanel {
    private BufferedImage spriteSheet;
    private BufferedImage[] idleFrames;
    private int currentFrame = 0; // Current frame in the animation
    private Timer animationTimer;

    public DogAnimation() {
        // Load the sprite sheet
        try {
            spriteSheet = ImageIO.read(new File("Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/2 Dog 2/Attack.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Load the images for idle animation frames
        idleFrames = new BufferedImage[4];

        // Assuming each sprite in the sheet is 64x64 pixels and there are 3 frames
        int frameWidth = 48;
        int frameHeight = 48;
        int numberOfFrames = 4; // Change this based on the number of sprites you have

        // Extract frames from the sprite sheet
        idleFrames = new BufferedImage[numberOfFrames];
        for (int i = 0; i < numberOfFrames; i++) {
            idleFrames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }

        // Set up a timer to update the frame every 100 milliseconds
        animationTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame = (currentFrame + 1) % idleFrames.length;
                repaint(); // Repaint the panel to show the updated frame
            }
        });
        animationTimer.start(); // Start the animation timer
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Scaling factor (e.g., 2.0 for double size)
        double scaleFactor = 10.0;

        int scaledWidth = (int)(idleFrames[currentFrame].getWidth() * scaleFactor);
        int scaledHeight = (int)(idleFrames[currentFrame].getHeight() * scaleFactor);

        // Draw the current frame of the Dog object
        g.drawImage(idleFrames[currentFrame], 50, 50, scaledWidth, scaledHeight, this); // Position at (50,50) for example
    }
}
