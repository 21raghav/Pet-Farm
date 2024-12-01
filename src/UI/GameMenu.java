package UI;

import Game.DataManager;
import Pets.Pet;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
    private Image questionImage;
    private Image saveImage;
    private Image vetImage;
    private Image petImage;

    //game should not be taking these stats but the database stats
    private int health = 10;
    private int happiness = 10;
    private int sleep = 10;
    private int hunger = 10;
    ///////////////////////////////////
    
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
            questionImage = ImageIO.read(new File("Assets/Images/question.png")); // Load the inventory image
            saveImage = ImageIO.read(new File("Assets/Images/SaveGame.png")); // Load the inventory image
            petImage = ImageIO.read(new File("Assets/Images/PetShelterButton.png")); // Load the inventory image
            vetImage = ImageIO.read(new File("Assets/Images/VetShelterButton.png")); // Load the inventory image
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

        ImageIcon petIcon = new ImageIcon(petImage); // Wrap the image in an ImageIcon
        sleepButton = new JButton(petIcon); // Initialize the petShelter button
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
                this.dispose();
                new PetShelter(this.petToSpawn, stats);//new statistics(health, happiness, hunger, sleep, statBarImage, healthIcon, happyIcon, foodIcon, sleepIcon, this));
        }
            catch(Exception error) { error.printStackTrace();}
            //mainPanel.requestFocusInWindow();  // regain focus after interaction
        });

        ImageIcon vet = new ImageIcon(vetImage);
        vetButton = new JButton(vet);
        vetButton.setVisible(true);
        vetButton.setBounds(200, 350, vet.getIconWidth(), vet.getIconHeight());
        vetButton.setBorderPainted(false); // Do not paint the border
        vetButton.setContentAreaFilled(false); // Do not fill the content area
        vetButton.setFocusPainted(false); // Do not paint the focus indicator
        vetButton.setOpaque(false); // Set the button to be transparent
        mainPanel.add(vetButton);

        vetButton.addActionListener(e -> {
            try {
                this.dispose();
                new VetShelter(this.petToSpawn, stats); //new statistics(health, happiness, hunger, sleep, statBarImage, healthIcon, happyIcon, foodIcon, sleepIcon, this));
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
            mainPanel.requestFocusInWindow();  // regain focus after interaction

            // Add a WindowListener to the questionsWindow to detect when it is closed
            questionsWindow.addWindowListener((WindowListener) new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    question1Button.setEnabled(true);  // Re-enable the button once the window is closed
                }
            });
        });

        // Initialize the Question 2 button
        ImageIcon questionIcon2 = new ImageIcon(questionImage);
        question2Button = new JButton(questionIcon2);
        question2Button.setVisible(true);
        question2Button.setBounds(getWidth() - 300, (getHeight() - 30) / 2, questionIcon2.getIconWidth(), questionIcon2.getIconHeight()); //position/size
        question2Button.setBorderPainted(false); // Do not paint the border
        question2Button.setContentAreaFilled(false); // Do not fill the content area
        question2Button.setFocusPainted(false); // Do not paint the focus indicator
        question2Button.setOpaque(false); // Set the button to be transparent
        mainPanel.add(question2Button);
        // Set the position of the Question 2 button (right side)
        
        // Action listener for Question 2 button
        question2Button.addActionListener(e -> {
            question2Button.setEnabled(false);  // Disable the button to prevent re-clicks
            Questions questionsWindow = new Questions(inventory, stats, 1); // Open the Questions window !! TYPE 1 = GAMEMENU
            mainPanel.requestFocusInWindow();  // regain focus after interaction

            // Add a WindowListener to the questionsWindow to detect when it is closed
            questionsWindow.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    question2Button.setEnabled(true);  // Corrected to re-enable question2Button
                }
            });
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

    private void updateInventoryButtonPosition(JButton inventoryButton) {
        int buttonX = getWidth() - inventoryIcon.getWidth(null) - 20;
        int buttonY = 300;
        inventoryButton.setBounds(buttonX, buttonY, inventoryIcon.getWidth(null), inventoryIcon.getHeight(null));
    }
}
