package UI;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameMenu extends JFrame {
    private Image backgroundImage;
    private Image dogImage;
    private Image statBarImage; // New variable for the stat bar image
    private int dogX, dogY;  // Dog's position
    private int dogSpeedX = 5, dogSpeedY = 5;  // Dog's movement speed
    private int health = 50; // Health percentage

    public GameMenu() {
        setTitle("Game Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Set the frame to full screen

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("Assets/GameImages/GameMenu.png"));
            dogImage = ImageIO.read(new File("Assets/Idle.png"));
            statBarImage = ImageIO.read(new File("Assets/Images/statbar.png")); // Load the stat bar image
            if (dogImage == null) {
                throw new IOException("Dog image could not be loaded.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading images: " + e.getMessage(), "Image Load Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw the background image
                g.drawImage(dogImage, dogX, dogY, this); // Draw the dog image
                
                // Draw the stat bar at the top right corner
                int statBarWidth = statBarImage.getWidth(null);
                int statBarHeight = statBarImage.getHeight(null);
                int statBarX = getWidth() - statBarWidth - 10;
                int statBarY = 10;

                g.drawImage(statBarImage, statBarX, statBarY, this); // Positioning the stat bar

                // Draw the health bar (filled with red) within the stat bar
                g.setColor(Color.RED);
                g.fillRect(statBarX + 1, statBarY + 1, (int)(statBarWidth * (health / 100.0)), statBarHeight- 2); // Fill based on health
            }
        };

        mainPanel.setLayout(null);
        add(mainPanel);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt) {
                dogX = (getWidth() - dogImage.getWidth(null)) / 2;
                dogY = (getHeight() - dogImage.getHeight(null)) /2;
            }
        });

        mainPanel.setFocusable(true);
        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                moveDog(e.getKeyCode());
                mainPanel.repaint();
            }
        });

        setVisible(true);
    }

    private void moveDog(int keyCode) {
        int frameWidth = getWidth();
        int halfFrameHeight = (getHeight() / 2) + 50;  // Midpoint of the screen for the top boundary
        int fullFrameHeight = getHeight();      // Full height for the bottom boundary
        int imageWidth = dogImage.getWidth(null);
        int imageHeight = dogImage.getHeight(null);
    
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                if (dogX - dogSpeedX >= 0) { // Check if moving left goes out of bounds
                    dogX -= dogSpeedX;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (dogX + dogSpeedX + imageWidth <= frameWidth) { // Check if moving right goes out of bounds
                    dogX += dogSpeedX;
                }
                break;
            case KeyEvent.VK_UP:
                if (dogY - dogSpeedY >= 0 && dogY - dogSpeedY + imageHeight >= halfFrameHeight) { // Check if moving up goes beyond half the frame height
                    dogY -= dogSpeedY;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (dogY + dogSpeedY + imageHeight <= fullFrameHeight) { // Check if moving down goes out of bounds
                    dogY += dogSpeedY;
                }
                break;
        }
    
        // Ensure the component is repainted after every movement
        repaint();
    }    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameMenu::new);
    }
}