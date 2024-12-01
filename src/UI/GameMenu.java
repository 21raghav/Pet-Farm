package UI;

import Game.DataManager;
import Pets.Pet;
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
    private Image happyIcon;
    private Image foodIcon;
    private Image sleepIcon;
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
            happyIcon = ImageIO.read(new File("Assets/Images/happyicon.png")); // Load the health icon image
            foodIcon = ImageIO.read(new File("Assets/Images/foodicon.png")); // Load the health icon image
            sleepIcon = ImageIO.read(new File("Assets/Images/sleepicon.png")); // Load the health icon image
            inventoryIcon = ImageIO.read(new File("Assets/Images/InventoryIcon.png")); // Load the inventory icon image
            inventoryImage = ImageIO.read(new File("Assets/Images/Inventory.png")); // Load the inventory image
            stats = new statistics(health, happiness, hunger, sleep, statBarImage, healthIcon, happyIcon, foodIcon, sleepIcon, this); // Initialize statistics

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

        //pet spawn
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
        mainPanel.repaint();
        inventoryButton.addActionListener(e -> {
            inventory.toggleInventoryDisplay();
            mainPanel.requestFocusInWindow();  // regain focus after interaction
        });

        mainPanel.repaint();

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
                new PetShelter(this.petToSpawn, new statistics(health, happiness, hunger, sleep, statBarImage, healthIcon, happyIcon, foodIcon, sleepIcon, this));
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
                new VetShelter(this.petToSpawn, new statistics(health, happiness, hunger, sleep, statBarImage, healthIcon, happyIcon, foodIcon, sleepIcon, this));
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

    private void updateInventoryButtonPosition(JButton inventoryButton) {
        int buttonX = getWidth() - inventoryIcon.getWidth(null) - 20;
        int buttonY = 300;
        inventoryButton.setBounds(buttonX, buttonY, inventoryIcon.getWidth(null), inventoryIcon.getHeight(null));
    }
}
