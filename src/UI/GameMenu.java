package UI;


import Game.DataManager;
import Pets.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

    private Map<String,String> data;

    private Image questionImage;
    private Image saveImage;
    private Image vetImage;
    private Image petImage;

    private int health;
    private int happiness;
    private int sleep;
    private int hunger;

    private statistics stats; // Reference to the statistics class
    private Inventory inventory; // Reference to the Inventory class
    //    private final JButton sleepButton; // New variable for the sleep button
    private final JButton question1Button; // New variable for Question 1 button
    private final JButton question2Button; // New variable for Question 2 button
    private final Pet petToSpawn;
    private String saveFileName;

    public GameMenu(Pet petToSpawn) {

        this.petToSpawn = petToSpawn; // Save the pet instance for save functionality

        Map<String,String> data = this.loadData();
        this.initializeStatistics(data);

        this.data = this.loadData();
        this.initializeStatistics(this.data);

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
            stats = new statistics(this.saveFileName, this.loadData(), statBarImage, healthIcon, happyIcon, foodIcon, sleepIcon, this); // Initialize statistics
            questionImage = ImageIO.read(new File("Assets/Images/question.png")); // Load the inventory image
            saveImage = ImageIO.read(new File("Assets/Images/SaveGame.png")); // Load the inventory image
            petImage = ImageIO.read(new File("Assets/Images/PetShelterButton.png")); // Load the inventory image
            vetImage = ImageIO.read(new File("Assets/Images/VetShelterButton.png")); // Load the inventory image
//            stats = new statistics(health, happiness, hunger, sleep, statBarImage, healthIcon, happyIcon, foodIcon, sleepIcon, this); // Initialize statistics


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

        Timer decreaseAll = new Timer(20000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stats.updateState(1,-5);
                stats.updateState(2,-3);
                stats.updateState(3,-4);

            }
        });

        decreaseAll.setRepeats(true); // Ensure the timer continues to repeat
        decreaseAll.start(); // Start the timer

        mainPanel.setLayout(null);

        ImageIcon saveIcon = new ImageIcon(saveImage); // Wrap the image in an ImageIcon
        JButton saveButton = new JButton(saveIcon); // Create the button with the image icon
        saveButton.setBounds(50, 50, saveIcon.getIconWidth(), saveIcon.getIconHeight()); // Adjust button size to image size
        saveButton.setBorderPainted(false); // Do not paint the border
        saveButton.setContentAreaFilled(false); // Do not fill the content area
        saveButton.setFocusPainted(false); // Do not paint the focus indicator
        saveButton.setOpaque(false); // Set the button to be transparent

        saveButton.addActionListener(e -> {
            DataManager.saveState(petToSpawn.getClass().getSimpleName().toLowerCase(), petToSpawn.getAttributes());
            JOptionPane.showMessageDialog(this, "Game saved!");
        });

        mainPanel.add(saveButton); // Add the button to your panel


        //pet spawn
        JPanel character = petToSpawn.getAnimationPanel();
        if(stats.getHealth() == 0){
            petToSpawn.sleep();
            SwingUtilities.invokeLater(() -> {
                // Create a non-modal dialog to show the message
                final JDialog dialog = new JDialog();
                dialog.setTitle("Alert");
                dialog.setModal(false); // Make it non-modal
                dialog.setSize(600, 100);
                dialog.setLayout(new FlowLayout());

                // Create the label with a custom font
                JLabel messageLabel = new JLabel("Your pet has died! Health is empty...");
                messageLabel.setFont(new Font("Serif", Font.BOLD, 24)); // Set the font size to 24 and make it bold
                dialog.add(messageLabel);

                dialog.setLocationRelativeTo(null); // Center on screen

                // Set a timer to close the dialog automatically after 6 seconds
                new Timer(6000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                        dispose();
                    }
                }).start();

                dialog.setVisible(true);
            });

        }
        else{
            petToSpawn.unlock();
            petToSpawn.stopWalking();
        }

        character.setBounds(100, 100, character.getPreferredSize().width, character.getPreferredSize().height);
        mainPanel.add(character);

        add(mainPanel);

        mainPanel.setFocusable(true);

        // Define JLabel to display the score
        JLabel scoreLabel = new JLabel();
        scoreLabel.setHorizontalAlignment(JLabel.CENTER); // Center align text
        scoreLabel.setVerticalAlignment(JLabel.TOP);
        scoreLabel.setFont(new Font("Serif", Font.BOLD, 30)); // Set font size and style
        scoreLabel.setForeground(Color.BLACK); // Set text color to white
        // Set the initial score
        int initialScore = stats.getScore(); // Assuming stats has a method getScore()
        scoreLabel.setText("Score: " + initialScore);
        // Position the score label at the middle right of the panel
        scoreLabel.setBounds(680, 95, 300, 300);
        mainPanel.add(scoreLabel); // Add the score label to your panel
        // Timer to update the score label every second (1000 milliseconds)
        Timer scoreUpdater = new Timer(1000, e -> {
            int currentScore = stats.getScore();
            scoreLabel.setText("Score: " + currentScore);
        });
        scoreUpdater.start();

        // Setup inventory button and inventory class
        JButton inventoryButton = new JButton(new ImageIcon(inventoryIcon));
        inventoryButton.setContentAreaFilled(false);
        inventoryButton.setBorderPainted(false);
        inventoryButton.setFocusPainted(false);
        mainPanel.add(inventoryButton);
        updateInventoryButtonPosition(inventoryButton);

        // Initialize the Inventory with the statistics instance
        inventory = new Inventory(this.saveFileName, this, stats, inventoryImage, inventoryButton, this.data);
        mainPanel.repaint();
        inventoryButton.addActionListener(e -> {
            inventory.toggleInventoryDisplay();
            mainPanel.requestFocusInWindow();  // regain focus after interaction
        });

        mainPanel.repaint();

        // Initialize the sleep button
        // New variable for the sleep button
        ImageIcon petIcon = new ImageIcon(petImage); // Wrap the image in an ImageIcon
        JButton sleepButton = new JButton(petIcon); // Initialize the petShelter button
        sleepButton.setVisible(true); // Initially hidden
        sleepButton.setVisible(true);
        sleepButton.setBounds(550, 300, petIcon.getIconWidth(), petIcon.getIconHeight()); // position and size
        sleepButton.setBorderPainted(false); // Do not paint the border
        sleepButton.setContentAreaFilled(false); // Do not fill the content area
        sleepButton.setFocusPainted(false); // Do not paint the focus indicator
        sleepButton.setOpaque(false); // Set the button to be transparent
        mainPanel.add(sleepButton);

        // Action listener for the sleep button
        sleepButton.addActionListener(e -> {
            try {
                if(stats.getHappiness() > 0){
                    this.dispose();
                    new PetShelter(this.petToSpawn, new statistics(this.saveFileName, this.loadData(), statBarImage, healthIcon, happyIcon, foodIcon, sleepIcon, this));
                }
            }
                catch(Exception error) { error.printStackTrace();}
                //mainPanel.requestFocusInWindow();  // regain focus after interaction
            });

        ImageIcon vet = new ImageIcon(vetImage);
        JButton vetButton = new JButton(vet);
        vetButton.setVisible(true);
        vetButton.setBounds(150, 300, vet.getIconWidth(), vet.getIconHeight());
        vetButton.setBorderPainted(false); // Do not paint the border
        vetButton.setContentAreaFilled(false); // Do not fill the content area
        vetButton.setFocusPainted(false); // Do not paint the focus indicator
        vetButton.setOpaque(false); // Set the button to be transparent
        mainPanel.add(vetButton);

        vetButton.addActionListener(e -> {
            try {
                if(stats.getHappiness() > 0) {
                    this.dispose();
                    new VetShelter(this.petToSpawn, new statistics(this.saveFileName, this.loadData(), statBarImage, healthIcon, happyIcon, foodIcon, sleepIcon, this));
                }
            } catch(Exception error) { error.printStackTrace();}
//            mainPanel.repaint();
        });

        ImageIcon questionIcon1 = new ImageIcon(questionImage); // Wrap the image in an ImageIcon
        question1Button = new JButton(questionIcon1); // Initialize the petShelter button
        question1Button.setVisible(true);
        question1Button.setBounds(750, 350, questionIcon1.getIconWidth(), questionIcon1.getIconHeight()); // position and size
        question1Button.setBorderPainted(false); // Do not paint the border
        question1Button.setContentAreaFilled(false); // Do not fill the content area
        question1Button.setFocusPainted(false); // Do not paint the focus indicator
        question1Button.setOpaque(false); // Set the button to be transparent
        mainPanel.add(question1Button);

        // Action listener for Question 1 button
        question1Button.addActionListener(e -> {
            question1Button.setEnabled(false);  // Disable the button to prevent re-clicks
            Questions questionsWindow = new Questions(inventory, stats, 1); // Open the Questions window !! TYPE 1 = GAMEMENU
            mainPanel.requestFocusInWindow();  // Regain focus after interaction
            mainPanel.repaint();

            // Timer to re-enable the button after 15 seconds (15000 milliseconds)
            Timer enableButtonTimer = new Timer(15000, event -> {
                question1Button.setEnabled(true);  // Re-enable the button after 30 seconds
            });
            enableButtonTimer.setRepeats(false);  // Ensure the timer only runs once
            enableButtonTimer.start();  // Start the timer
        });

        // Initialize the Question 2 button
        ImageIcon questionIcon2 = new ImageIcon(questionImage);
        question2Button = new JButton(questionIcon2);
        question2Button.setVisible(true);
        question2Button.setBounds(getWidth() - 3000, (getHeight() - 30) / 2, questionIcon2.getIconWidth(), questionIcon2.getIconHeight()); //position/size
        question2Button.setBorderPainted(false); // Do not paint the border
        question2Button.setContentAreaFilled(false); // Do not fill the content area
        question2Button.setFocusPainted(false); // Do not paint the focus indicator
        question2Button.setOpaque(false); // Set the button to be transparent
        mainPanel.add(question2Button);
        // Set the position of the Question 2 button (right side)

        // Action listener for Question 1 button
        question2Button.addActionListener(e -> {
            question2Button.setEnabled(false);  // Disable the button to prevent re-clicks
            Questions questionsWindow = new Questions(inventory, stats, 1); // Open the Questions window !! TYPE 1 = GAMEMENU
            mainPanel.requestFocusInWindow();  // Regain focus after interaction
            mainPanel.repaint();
            // Timer to re-enable the button after 15 seconds (15000 milliseconds)
            Timer enableButtonTimer = new Timer(15000, event -> {
                question2Button.setEnabled(true);  // Re-enable the button after 30 seconds
            });
            enableButtonTimer.setRepeats(false);  // Ensure the timer only runs once
            enableButtonTimer.start();  // Start the timer
        });

        // Component listeners for resizing and button updates
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                updateInventoryButtonPosition(inventoryButton);
                // Update positions of question buttons on resize
                question1Button.setBounds(220, (getHeight() - 30) / 2 + 150, questionIcon1.getIconWidth(), questionIcon1.getIconHeight());
                question2Button.setBounds(getWidth() - 330, (getHeight() - 30) / 2 + 150, questionIcon2.getIconWidth(), questionIcon2.getIconHeight());
            }
        });

        mainPanel.setFocusable(true);
        mainPanel.requestFocusInWindow();

        KeyboardListener animalControls = new KeyboardListener(petToSpawn);
        mainPanel.addKeyListener(animalControls);

        setVisible(true);
    }

    private Map<String, String> loadData() {
        if (this.petToSpawn instanceof Dog) {
            this.saveFileName = "slot1.csv";
        }
        else if (this.petToSpawn instanceof Cat) {
            this.saveFileName = "slot2.csv";
        }
        else if (this.petToSpawn instanceof Fox) {
            this.saveFileName = "slot3.csv";
        }
        else if (this.petToSpawn instanceof Rat) {
            this.saveFileName = "slot4.csv";
        }
        else {
            return new HashMap<>();
        }

        return DataManager.loadState("", this.saveFileName);
    }

    private void initializeStatistics(Map<String,String> data) {
        this.health = Integer.parseInt(data.get("health"));
        this.happiness = Integer.parseInt(data.get("happiness"));
        this.hunger = Integer.parseInt(data.get("hunger"));
        this.sleep = Integer.parseInt(data.get("sleep"));
    }

    private void updateInventoryButtonPosition(JButton inventoryButton) {
        int buttonX = getWidth() - inventoryIcon.getWidth(null) - 20;
        int buttonY = 300;
        inventoryButton.setBounds(buttonX, buttonY, inventoryIcon.getWidth(null), inventoryIcon.getHeight(null));
    }
}
