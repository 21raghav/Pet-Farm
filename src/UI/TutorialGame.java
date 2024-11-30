package UI;

import Consumable.FoodConsumable;
import Consumable.SleepConsumable;
import Pets.Dog;
import Pets.Pet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class TutorialGame extends JFrame {
    private Pet tutorialPet;
    private int hunger = 70;
    private int happiness = 70;

    private JProgressBar hungerBar, happinessBar;
    private JLabel messageLabel;

    private int currentObjective = 1;

    // Movement counters
    private final Map<String, Integer> movementCounts = new HashMap<>();

    public TutorialGame(Pet tutorialPet) {
        this.tutorialPet = tutorialPet;

        // Initialize movement counters
        movementCounts.put("up", 0);
        movementCounts.put("down", 0);
        movementCounts.put("left", 0);
        movementCounts.put("right", 0);

        setTitle("Interactive Tutorial");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel petPanel = tutorialPet.getAnimationPanel();
        mainPanel.add(petPanel, BorderLayout.CENTER);

        // Make the main panel focusable and request focus
        mainPanel.setFocusable(true);
        mainPanel.requestFocusInWindow();

        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        hungerBar = createProgressBar(hunger);
        happinessBar = createProgressBar(happiness);

        statsPanel.add(new JLabel("Hunger:"));
        statsPanel.add(hungerBar);
        statsPanel.add(new JLabel("Happiness:"));
        statsPanel.add(happinessBar);

        mainPanel.add(statsPanel, BorderLayout.NORTH);

        // Message display
        messageLabel = new JLabel("Objective 1: Use W, A, S, D to move the pet (2 times each)!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
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
        JButton feedButton = new JButton("Feed");
        feedButton.addActionListener(e -> handleAction("Feed"));
        JButton sleepButton = new JButton("Sleep");
        sleepButton.addActionListener(e -> handleAction("Sleep"));
        buttonPanel.add(feedButton);
        buttonPanel.add(sleepButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Ensure focus is set to the window
        SwingUtilities.invokeLater(() -> {
            this.setFocusable(true);
            this.requestFocusInWindow();
        });
    }

    private JProgressBar createProgressBar(int value) {
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(value);
        progressBar.setStringPainted(true);
        return progressBar;
    }

    private void handleMovement(KeyEvent e) {
        int moveAmount = 10;
        String direction = null;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                tutorialPet.move(0, -moveAmount);
                tutorialPet.walk();
                direction = "up";
                messageLabel.setText("You moved up!");
            }
            case KeyEvent.VK_A -> {
                tutorialPet.move(-moveAmount, 0);
                tutorialPet.walk();
                direction = "left";
                messageLabel.setText("You moved left!");
            }
            case KeyEvent.VK_S -> {
                tutorialPet.move(0, moveAmount);
                tutorialPet.walk();
                direction = "down";
                messageLabel.setText("You moved down!");
            }
            case KeyEvent.VK_D -> {
                tutorialPet.move(moveAmount, 0);
                tutorialPet.walk();
                direction = "right";
                messageLabel.setText("You moved right!");
            }
        }

        if (direction != null) {
            int count = movementCounts.get(direction) + 1;
            movementCounts.put(direction, count);
            checkMovementObjective();
        }
    }

    private void checkMovementObjective() {
        boolean allMovementsCompleted = movementCounts.values().stream().allMatch(count -> count >= 2);

        if (allMovementsCompleted && currentObjective == 1) {
            currentObjective++;
            messageLabel.setText("Objective 2: Feed the pet using the Feed button!");
        }
    }

    private void handleAction(String action) {
        if (action.equals("Feed") && currentObjective == 2) {
            FoodConsumable food = new FoodConsumable(20);
            if (food.consume(tutorialPet)) {
                hunger = Math.min(hunger + 20, 100);
                hungerBar.setValue(hunger);
                messageLabel.setText("You fed the pet! Hunger increased.");
                currentObjective++;
                messageLabel.setText("Objective 3: Let the pet rest using the Sleep button!");
            }
        } else if (action.equals("Sleep") && currentObjective == 3) {
            SleepConsumable sleep = new SleepConsumable(20);
            if (sleep.consume(tutorialPet)) {
                happiness = Math.min(happiness + 20, 100);
                happinessBar.setValue(happiness);
                messageLabel.setText("The pet rested! Happiness increased.");
                JOptionPane.showMessageDialog(this, "Tutorial Completed! Great job!");
                dispose(); // Close tutorial
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TutorialGame(new Dog("Tutorial Dog")).setVisible(true));
    }
}
