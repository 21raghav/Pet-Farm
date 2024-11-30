package UI;

import Pets.Cat;
import Pets.Dog;
import Pets.Pet;
import Pets.Rat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class GameMenu extends JFrame {
    private Image backgroundImage;
    private Image statBarImage; // New variable for the stat bar image
    private Image healthIcon; // New variable for the health icon
    private Image inventoryIcon; // New variable for the inventory icon
    private Image inventoryImage; // New variable for inventory image
    private int health = 100; // Health percentage
    private int happiness = 80; // Happiness percentage
    private int hunger = 30; // Hunger percentage
    private int sleep = 70; // Sleep percentage

    private Pet petToSpawn;

    public GameMenu(Pet petToSpawn) {
        setTitle("Game Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Set the frame to full screen

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("Assets/GameImages/GameMenu.png"));
            statBarImage = ImageIO.read(new File("Assets/Images/statbar.png")); // Load the stat bar image
            healthIcon = ImageIO.read(new File("Assets/Images/healthicon.png")); // Load the health icon image
            inventoryIcon = ImageIO.read(new File("Assets/Images/InventoryIcon.png")); // Load the inventory icon image
            inventoryImage = ImageIO.read(new File("Assets/Images/Inventory.png")); // Load the inventory image

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

                // Draw the stat bar at the top right corner
                int statBarWidth = statBarImage.getWidth(null);
                int statBarHeight = statBarImage.getHeight(null);
                int statBarX = getWidth() - statBarWidth - 60;
                int statBarY = 10;

                // Draw health bar
                g.drawImage(statBarImage, statBarX, statBarY, this); // Positioning the health stat bar
                int healthFillWidth = (int)(statBarWidth * (health / 100.0)) - 4; // Subtracting for outline
                g.setColor(Color.RED);
                g.fillRect(statBarX + 2, statBarY + 2, healthFillWidth, statBarHeight - 4); // Fill health bar

                // Draw health icon next to the health bar
                g.drawImage(healthIcon, statBarX + statBarWidth + 5, statBarY, this); // Positioning the health icon

                // Draw happiness bar
                statBarY += statBarHeight + 10; // Move down for the happiness bar
                g.drawImage(statBarImage, statBarX, statBarY, this); // Positioning the happiness stat bar
                int happinessFillWidth = (int)(statBarWidth * (happiness / 100.0)) - 4; // Subtracting for outline
                g.setColor(Color.RED); // Different color for happiness
                g.fillRect(statBarX + 2, statBarY + 2, happinessFillWidth, statBarHeight - 4); // Fill happiness bar

                // Draw hunger bar
                statBarY += statBarHeight + 10; // Move down for the hunger bar
                g.drawImage(statBarImage, statBarX, statBarY, this); // Positioning the hunger stat bar
                int hungerFillWidth = (int)(statBarWidth * (hunger / 100.0)) - 4; // Subtracting for outline
                g.setColor(Color.RED); // Different color for hunger
                g.fillRect(statBarX + 2, statBarY + 2, hungerFillWidth, statBarHeight - 4); // Fill hunger bar

                // Draw sleep bar
                statBarY += statBarHeight + 10; // Move down for the sleep bar
                g.drawImage(statBarImage, statBarX, statBarY, this); // Positioning the sleep stat bar
                int sleepFillWidth = (int)(statBarWidth * (sleep / 100.0)) - 4; // Subtracting for outline
                g.setColor(Color.RED); // Different color for sleep
                g.fillRect(statBarX + 2, statBarY + 2, sleepFillWidth, statBarHeight - 4); // Fill sleep bar
            }
        };

        mainPanel.setLayout(null);

        JPanel character = petToSpawn.getAnimationPanel();
        character.setBounds(100, 100, character.getPreferredSize().width, character.getPreferredSize().height);
        mainPanel.add(character);

        add(mainPanel);


        mainPanel.setFocusable(true);

        // Create Inventory Button with Icon
        JButton inventoryButton = new JButton(new ImageIcon(inventoryIcon));
        inventoryButton.setContentAreaFilled(false); // Make the button transparent
        inventoryButton.setBorderPainted(false);     // Remove the button border
        inventoryButton.setFocusPainted(false);     // Remove the focus outline
        inventoryButton.addActionListener(e -> showInventoryDialog());
        mainPanel.add(inventoryButton);

        // Update button position initially
        updateInventoryButtonPosition(inventoryButton);

        // Add a component listener to update button position on resize
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                updateInventoryButtonPosition(inventoryButton);
            }
        });

        mainPanel.setFocusable(true);
        mainPanel.requestFocusInWindow();

        KeyboardListener animalControls = new KeyboardListener(petToSpawn);
        mainPanel.addKeyListener(animalControls);

        setVisible(true);
    }

    private void showInventoryDialog() {
        // New variable for inventory item counts
        int[] itemCounts = {5, 3, 2, 4, 1, 6}; // Example counts for each item

        JDialog inventoryDialog = new JDialog(this, "Inventory", false);
        inventoryDialog.setSize(525, 225);
        inventoryDialog.setLocationRelativeTo(this);
        inventoryDialog.setResizable(false); // Prevent resizing of the dialog

        JPanel inventoryPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (inventoryImage != null) {
                    g.drawImage(inventoryImage, 0, 0, getWidth(), getHeight(), this);
                }

                // Set font size
                g.setFont(new Font("Arial", Font.BOLD, 20)); // Increase font size

                // Draw inventory item counts at specific coordinates with different colors
                g.setColor(Color.RED);
                g.drawString(String.valueOf(itemCounts[0]), 145, 120); // Strawberry count

                g.setColor(Color.ORANGE);
                g.drawString(String.valueOf(itemCounts[1]), 235, 120); // Orange count

                g.setColor(Color.YELLOW);
                g.drawString(String.valueOf(itemCounts[2]), 320, 120); // Banana count

                g.setColor(Color.GREEN);
                g.drawString(String.valueOf(itemCounts[3]), 60, 120); // Apple count

                g.setColor(Color.BLUE);
                g.drawString(String.valueOf(itemCounts[4]), 405, 120); // Treat1 count

                g.setColor(Color.MAGENTA);
                g.drawString(String.valueOf(itemCounts[5]), 490, 120); // Treat2 count
            }
        };

        inventoryDialog.add(inventoryPanel);
        inventoryDialog.setVisible(true);
    }

    private void updateInventoryButtonPosition(JButton inventoryButton) {
        // Calculate the new position based on the current window size
        int buttonX = getWidth() - inventoryIcon.getWidth(null) - 20; // Adjust X position to move it more to the right
        int buttonY = 300; // Keep Y position constant or adjust as needed
        inventoryButton.setBounds(buttonX, buttonY, inventoryIcon.getWidth(null), inventoryIcon.getHeight(null));
    }


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new GameMenu(this.petToSpawn));
//    }
}