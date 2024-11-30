package UI;

import java.awt.*;
import javax.swing.*;

public class VetShelter {
    private int dogX;
    private int dogY;
    private final JPanel backgroundPanel; // Background panel
    private final statistics stats; // Statistics instance
    private int health;
    private int happiness;
    private int sleep;
    private int hunger;

    public VetShelter(Image dogImage, int health, int happiness, int sleep, int hunger) {

        JFrame frame = new JFrame("Vet Shelter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         // Initialize statistics with example values
         Image statBarImage = new ImageIcon("Assets/Images/statbar.png").getImage(); // Load your stat bar image
         Image healthIcon = new ImageIcon("Assets/Images/healthicon.png").getImage(); // Load your health icon image
         stats = new statistics(health, happiness, hunger, sleep, statBarImage, healthIcon); // Example values

        backgroundPanel = new JPanel() {
            private final Image backgroundImage = new ImageIcon("Assets/GameImages/vetshelter.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g.drawImage(dogImage, dogX, dogY, this); // Draw the dog image
                stats.drawStats(g, getWidth(), getHeight()); // Draw the statistics
            }
        };

        // Set the layout manager to null for absolute positioning of components
        backgroundPanel.setLayout(null);

        // Position the dog at the center bottom of the screen
        dogX = (Toolkit.getDefaultToolkit().getScreenSize().width - dogImage.getWidth(null)) / 2 + 40;
        dogY = Toolkit.getDefaultToolkit().getScreenSize().height - dogImage.getHeight(null) - 420; // 50 pixels above the bottom

        // Create an exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            frame.dispose();
        });

        // Set the button transparent and no border
        exitButton.setOpaque(true);
        exitButton.setContentAreaFilled(true);
        exitButton.setBorderPainted(true);

        // Position the button at the top-right corner
        exitButton.setBounds(10, 10, 100, 30); // Adjusted positioning

        // Add the exit button directly to the background panel
        backgroundPanel.add(exitButton);

        frame.getContentPane().add(backgroundPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        frame.setVisible(true);
    }
}
