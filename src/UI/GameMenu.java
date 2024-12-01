package UI;

import Pets.Pet;
import Game.DataManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
    private int dogX, dogY;  // Dog's position
    private int dogSpeedX = 10, dogSpeedY = 10;  // Dog's movement speed
    private int health = 10;
    private int happiness = 10;
    private int sleep = 10;
    private int hunger = 10;
    private statistics stats; // Reference to the statistics class
    private Inventory inventory; // Reference to the Inventory class
    private final JButton sleepButton; // New variable for the sleep button
    private final JButton vetButton; // New variable for the vet button
    private final JButton question1Button; // New variable for Question 1 button
    private final JButton question2Button; // New variable for Question 2 button
    private Pet petToSpawn;

    public GameMenu(Pet petToSpawn) {
        this.petToSpawn = petToSpawn; // Save the pet instance for save functionality

        setTitle("Game Menu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Set the frame to full screen


        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("Assets/GameImages/GameMenu.png"));
            statBarImage = ImageIO.read(new File("Assets/Images/statbar.png")); // Load the stat bar image
            healthIcon = ImageIO.read(new File("Assets/Images/healthicon.png")); // Load the health icon image
            inventoryIcon = ImageIO.read(new File("Assets/Images/InventoryIcon.png")); // Load the inventory icon image
            inventoryImage = ImageIO.read(new File("Assets/Images/Inventory.png")); // Load the inventory image
            stats = new statistics(health, happiness, hunger, sleep, statBarImage, healthIcon); // Initialize statistics

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
                stats.drawStats(g, getWidth(), getHeight()); // Draw the statistics

                // Draw the stat bar at the top right corner
                int statBarWidth = statBarImage.getWidth(null);
                int statBarHeight = statBarImage.getHeight(null);
                int statBarX = getWidth() - statBarWidth - 60;
                int statBarY = 10;

                // Draw health bar
                g.drawImage(statBarImage, statBarX, statBarY, this); // Positioning the health stat bar
                int healthFillWidth = (int) (statBarWidth * (health / 100.0)) - 4; // Subtracting for outline
                g.setColor(Color.RED);
                g.fillRect(statBarX + 2, statBarY + 2, healthFillWidth, statBarHeight - 4); // Fill health bar

                // Draw health icon next to the health bar
                g.drawImage(healthIcon, statBarX + statBarWidth + 5, statBarY, this); // Positioning the health icon

                // Draw happiness bar
                statBarY += statBarHeight + 10; // Move down for the happiness bar
                g.drawImage(statBarImage, statBarX, statBarY, this); // Positioning the happiness stat bar
                int happinessFillWidth = (int) (statBarWidth * (happiness / 100.0)) - 4; // Subtracting for outline
                g.setColor(Color.RED); // Different color for happiness
                g.fillRect(statBarX + 2, statBarY + 2, happinessFillWidth, statBarHeight - 4); // Fill happiness bar

                // Draw hunger bar
                statBarY += statBarHeight + 10; // Move down for the hunger bar
                g.drawImage(statBarImage, statBarX, statBarY, this); // Positioning the hunger stat bar
                int hungerFillWidth = (int) (statBarWidth * (hunger / 100.0)) - 4; // Subtracting for outline
                g.setColor(Color.RED); // Different color for hunger
                g.fillRect(statBarX + 2, statBarY + 2, hungerFillWidth, statBarHeight - 4); // Fill hunger bar

                // Draw sleep bar
                statBarY += statBarHeight + 10; // Move down for the sleep bar
                g.drawImage(statBarImage, statBarX, statBarY, this); // Positioning the sleep stat bar
                int sleepFillWidth = (int) (statBarWidth * (sleep / 100.0)) - 4; // Subtracting for outline
                g.setColor(Color.RED); // Different color for sleep
                g.fillRect(statBarX + 2, statBarY + 2, sleepFillWidth, statBarHeight - 4); // Fill sleep bar
            }
        };

        mainPanel.setLayout(null);

        // Add Save Button
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(50, 50, 100, 40); // Top-left position
        saveButton.addActionListener(e -> {
            DataManager.saveState(petToSpawn.getClass().getSimpleName().toLowerCase(), petToSpawn.getAttributes());
            JOptionPane.showMessageDialog(this, "Game saved!");
        });
        mainPanel.add(saveButton);


        JPanel character = petToSpawn.getAnimationPanel();
        petToSpawn.unlock();
        petToSpawn.stopWalking();

        character.setBounds(100, 100, character.getPreferredSize().width, character.getPreferredSize().height);
        mainPanel.add(character);

        add(mainPanel);

        mainPanel.setFocusable(true);

        // Setup inventory button and inventory class
        JButton inventoryButton = new JButton(new ImageIcon(inventoryIcon));
        inventoryButton.setContentAreaFilled(false);
        inventoryButton.setBorderPainted(false);
        inventoryButton.setFocusPainted(false);
        mainPanel.add(inventoryButton);
        updateInventoryButtonPosition(inventoryButton);

        // Initialize the Inventory with the statistics instance
        inventory = new Inventory(this, stats, inventoryImage, inventoryButton);
        inventoryButton.addActionListener(e -> {
            //mainPanel.repaint(); // Ensure the panel is repainted to show updated stats
            inventory.toggleInventoryDisplay();
            mainPanel.requestFocusInWindow();  // regain focus after interaction
        });

        // Initialize the sleep button
        sleepButton = new JButton("Sleep");
        sleepButton.setVisible(true); // Initially hidden
        mainPanel.add(sleepButton);

        // Set the position and size of the sleep button
        sleepButton.setBounds(550, 350, 100, 30); // Example position and size

        // Action listener for the sleep button
        sleepButton.addActionListener(e -> {
            try {
                this.dispose();
                new PetShelter(this.petToSpawn, health, happiness, sleep, hunger);
        }
            catch(Exception error) { error.printStackTrace();}
//            mainPanel.requestFocusInWindow();  // regain focus after interaction
        });

        vetButton = new JButton("Vet");
        vetButton.setVisible(true);
        mainPanel.add(vetButton);
        vetButton.setBounds(200, 350, 100, 30);

        vetButton.addActionListener(e -> {
            try {
                this.dispose();
                new VetShelter(this.petToSpawn, health, happiness, sleep, hunger);
            } catch(Exception error) { error.printStackTrace();}
//            mainPanel.repaint();
        });

        // Initialize the Question 1 button
        question1Button = new JButton("Question 1");
        question1Button.setVisible(true);
        mainPanel.add(question1Button);

        // Set the position of the Question 1 button (left side)
        question1Button.setBounds(50, (getHeight() - 30) / 2, 100, 30); // Example position

        // Action listener for Question 1 button
        question1Button.addActionListener(e -> {
            new Questions(); // Open the Questions window
            mainPanel.repaint();  // regain focus after interaction
        });

        // Initialize the Question 2 button
        question2Button = new JButton("Question 2");
        question2Button.setVisible(true);
        mainPanel.add(question2Button);

        // Set the position of the Question 2 button (right side)
        question2Button.setBounds(getWidth() - 150, (getHeight() - 30) / 2, 100, 30); // Example position

        // Action listener for Question 2 button
        question2Button.addActionListener(e -> {
            new Questions(); // Open the Questions window
            mainPanel.requestFocusInWindow();  // regain focus after interaction
        });

        // Component listeners for resizing and button updates
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                updateInventoryButtonPosition(inventoryButton);
                // Update positions of question buttons on resize
                question1Button.setBounds(50, (getHeight() - 30) / 2, 100, 30);
                question2Button.setBounds(getWidth() - 150, (getHeight() - 30) / 2, 100, 30);
            }
        });

        mainPanel.setFocusable(true);
        mainPanel.requestFocusInWindow();

        KeyboardListener animalControls = new KeyboardListener(petToSpawn);
        mainPanel.addKeyListener(animalControls);

        setVisible(true);
    }
//    public void repaintMainPanel() {
//        mainPanel.repaint(); // Repaint the main panel
//    }

    private void updateInventoryButtonPosition(JButton inventoryButton) {
        int buttonX = getWidth() - inventoryIcon.getWidth(null) - 20;
        int buttonY = 300;
        inventoryButton.setBounds(buttonX, buttonY, inventoryIcon.getWidth(null), inventoryIcon.getHeight(null));
    }


    private void saveGame() {
        DataManager.saveState(petToSpawn.getClass().getSimpleName().toLowerCase(), petToSpawn.getAttributes());
        JOptionPane.showMessageDialog(this, "Game saved successfully!", "Save Game", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showInventoryDialog() {
        int[] itemCounts = {5, 3, 2, 4, 1, 6}; // Example counts for each item

        JDialog inventoryDialog = new JDialog(this, "Inventory", false);
        inventoryDialog.setSize(525, 225);
        inventoryDialog.setLocationRelativeTo(this);
        inventoryDialog.setResizable(false);

        JPanel inventoryPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (inventoryImage != null) {
                    g.drawImage(inventoryImage, 0, 0, getWidth(), getHeight(), this);
                }

                g.setFont(new Font("Arial", Font.BOLD, 20));

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
}
