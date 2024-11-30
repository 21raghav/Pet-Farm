package UI;

import Consumable.FoodConsumable;
import Consumable.SleepConsumable;
import Pets.Dog;
import Pets.Pet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TutorialGame extends JFrame {
    private Pet tutorialPet;

    private JLabel messageLabel;

    private int currentObjective = 1;

    // Movement counters
    private final Map<String, Integer> movementCounts = new HashMap<>();

    private Image backgroundImage; // Background image

    public TutorialGame(Pet tutorialPet) {
        this.tutorialPet = tutorialPet;

        // Load the GameMenu background image
        try {
            backgroundImage = ImageIO.read(new File("Assets/GameImages/GameMenu.png"));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading background image: " + e.getMessage(), "Image Load Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Initialize movement counters
        movementCounts.put("left", 0);
        movementCounts.put("right", 0);

        setTitle("Interactive Tutorial");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Custom main panel with background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image scaled to the panel size
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());

        JPanel petPanel = tutorialPet.getAnimationPanel();
        mainPanel.add(petPanel, BorderLayout.CENTER);

        // Make the main panel focusable and request focus
        mainPanel.setFocusable(true);
        mainPanel.requestFocusInWindow();

        // Message display
        messageLabel = new JLabel("Objective 1: Use A and D to move the pet (2 times each)!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel.setForeground(Color.WHITE); // Set text color to contrast with the background
        mainPanel.add(messageLabel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        // Attach the key listener to the frame (not just the panel)
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleMovement(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                tutorialPet.stopWalking();
            }
        });

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make the button panel transparent
        JButton feedButton = new JButton("Feed");
        feedButton.addActionListener(e -> handleAction("Feed"));
        JButton sleepButton = new JButton("Sleep");
        sleepButton.addActionListener(e -> handleAction("Sleep"));
        buttonPanel.add(feedButton);
        buttonPanel.add(sleepButton);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        // Ensure focus is set to the window
        SwingUtilities.invokeLater(() -> {
            this.setFocusable(true);
            this.requestFocusInWindow();
        });
    }

    private void handleMovement(KeyEvent e) {
        int moveAmount = 10; // Adjust movement increment
        String direction = null;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> { // Move left
                tutorialPet.move(-moveAmount, 0);
                tutorialPet.walk();
                direction = "left";
                messageLabel.setText("You moved left!");
            }
            case KeyEvent.VK_D -> { // Move right
                tutorialPet.move(moveAmount, 0);
                tutorialPet.walk();
                direction = "right";
                messageLabel.setText("You moved right!");
            }
            default -> {
                // Ignore other key presses
            }
        }

        if (direction != null) {
            int count = movementCounts.get(direction) + 1;
            movementCounts.put(direction, count);
            checkMovementObjective();
        }
    }

    private void checkMovementObjective() {
        // Check if left and right movements meet the required count
        boolean leftCompleted = movementCounts.get("left") >= 2;
        boolean rightCompleted = movementCounts.get("right") >= 2;

        if (leftCompleted && rightCompleted && currentObjective == 1) {
            currentObjective++;
            messageLabel.setText("Objective 2: Feed the pet using the Feed button!");
        }
    }

    private void handleAction(String action) {
        if (action.equals("Feed") && currentObjective == 2) {
            FoodConsumable food = new FoodConsumable(20);
            if (food.consume(tutorialPet)) {
                messageLabel.setText("You fed the pet! Objective 3: Let the pet rest using the Sleep button!");
                currentObjective++;
            }
        } else if (action.equals("Sleep") && currentObjective == 3) {
            SleepConsumable sleep = new SleepConsumable(20);
            if (sleep.consume(tutorialPet)) {
                messageLabel.setText("The pet rested! Tutorial Completed! Great job!");
                JOptionPane.showMessageDialog(this, "Tutorial Completed! Great job!");
                dispose(); // Close tutorial
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TutorialGame(new Dog("Tutorial Dog")).setVisible(true));
    }
}
