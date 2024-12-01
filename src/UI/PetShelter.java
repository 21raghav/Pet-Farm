package UI;

import Pets.Pet;

import java.awt.*;
import javax.swing.*;

public class PetShelter {
    private int dogX; // Dog's X position
    private int dogY; // Dog's Y position
    private final JPanel backgroundPanel; // Background panel
    private final statistics stats; // Statistics instance
    private int health, happiness, sleep, hunger;

    public PetShelter(Pet animal, int health, int happiness, int sleep, int hunger) {
        JFrame frame = new JFrame("Pet Shelter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize statistics with example values
        Image statBarImage = new ImageIcon("Assets/Images/statbar.png").getImage(); // Load your stat bar image
        Image healthIcon = new ImageIcon("Assets/Images/healthicon.png").getImage(); // Load your health icon image
        stats = new statistics(health,happiness,hunger,sleep,statBarImage, healthIcon); // Example values

        backgroundPanel = new JPanel() {
            private final Image backgroundImage = new ImageIcon("Assets/GameImages/Petshelter.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                stats.drawStats(g, getWidth(), getHeight()); // Draw the statistics
            }
        };

        // Set the layout manager to null for absolute positioning of components
        backgroundPanel.setLayout(null);

        // Create an exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            frame.dispose();
        });
        exitButton.setOpaque(true);
        exitButton.setContentAreaFilled(true);
        exitButton.setBorderPainted(true);

        // Position the button at the top-right corner
        exitButton.setBounds(10, 10, 100, 30); // Adjusted positioning

        // Add the exit button directly to the background panel
        backgroundPanel.add(exitButton);

        JPanel character = animal.getAnimationPanel();
        backgroundPanel.add(character);

        backgroundPanel.setFocusable(true);
        backgroundPanel.requestFocusInWindow();
        frame.setVisible(true);
        
        frame.getContentPane().add(backgroundPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        frame.setVisible(true);
    }
}
