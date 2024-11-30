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
    private JPanel mainPanel;

    public GameMenu() {
        setTitle("Game Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Set the frame to full screen

        mainPanel = new JPanel();

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("Assets/GameImages/GameMenu.png"));
            dogImage = ImageIO.read(new File("Assets/Idle.png"));
            statBarImage = ImageIO.read(new File("Assets/Images/statbar.png")); // Load the stat bar image
            healthIcon = ImageIO.read(new File("Assets/Images/healthicon.png")); // Load the health icon image
            inventoryIcon = ImageIO.read(new File("Assets/Images/InventoryIcon.png")); // Load the inventory icon image
            inventoryImage = ImageIO.read(new File("Assets/Images/Inventory.png")); // Load the inventory image
            stats = new statistics(health, happiness, hunger, sleep, statBarImage, healthIcon); // Initialize statistics
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
                stats.drawStats(g, getWidth(), getHeight()); // Draw the statistics
            }
        };

        mainPanel.setLayout(null);
        add(mainPanel);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt) {
                dogX = (getWidth() - dogImage.getWidth(null)) / 2;
                dogY = (getHeight() - dogImage.getHeight(null)) / 2;
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
            new PetShelter(dogImage, health, happiness, sleep, hunger);
            mainPanel.requestFocusInWindow();  // regain focus after interaction
        });

        vetButton = new JButton("Vet");
        vetButton.setVisible(true);
        mainPanel.add(vetButton);
        vetButton.setBounds(200, 350, 100, 30);
        vetButton.addActionListener(e -> {
            new VetShelter(dogImage, health, happiness, sleep, hunger);
            mainPanel.requestFocusInWindow();
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
            mainPanel.requestFocusInWindow();  // regain focus after interaction
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

        setVisible(true);
    }

    private void updateInventoryButtonPosition(JButton inventoryButton) {
        int buttonX = getWidth() - inventoryIcon.getWidth(null) - 20;
        int buttonY = 300;
        inventoryButton.setBounds(buttonX, buttonY, inventoryIcon.getWidth(null), inventoryIcon.getHeight(null));
    }

    private void moveDog(int keyCode) {
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        int heightBound = (getHeight() / 2) - 150;
        int imageWidth = dogImage.getWidth(null);
        int imageHeight = dogImage.getHeight(null);

        switch (keyCode) {
            case KeyEvent.VK_A:
                if (dogX - dogSpeedX >= 0) {
                    dogX -= dogSpeedX;
                }
                break;
            case KeyEvent.VK_D:
                if (dogX + dogSpeedX + imageWidth <= frameWidth) {
                    dogX += dogSpeedX;
                }
                break;
            case KeyEvent.VK_W:
                if (dogY - dogSpeedY >= 0 && dogY >= heightBound) {
                    dogY -= dogSpeedY;
                }
                break;
            case KeyEvent.VK_S:
                if (dogY + dogSpeedY + imageHeight <= frameHeight - 50) {
                    dogY += dogSpeedY;
                }
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameMenu::new);
    }
}