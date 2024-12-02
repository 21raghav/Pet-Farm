package UI;

import Animation.DogAnimation;
import Pets.Dog;
import Pets.Pet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TutorialGame extends JFrame {
    private Pet tutorialPet;

    private JLabel messageLabel;
    private JLabel questionLabel;
    private JButton[] answerButtons;

    private int currentObjective = 1;

    // Movement counters
    private final Map<String, Integer> movementCounts = new HashMap<>();

    private Image backgroundImage; // Background image

    public TutorialGame(Pet tutorialPet) {
        this.tutorialPet = tutorialPet;

        // Load the background image
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

        // Open as a full-screen window
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLayout(new BorderLayout());

        // Custom main panel with background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(null);

        JPanel petPanel = tutorialPet.getAnimationPanel();
        petPanel.setBounds(300, 200, petPanel.getPreferredSize().width, petPanel.getPreferredSize().height);
        mainPanel.add(petPanel);

        messageLabel = new JLabel("Objective 1: Use A and D to move the pet (2 times each)!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel.setForeground(Color.BLACK);
        messageLabel.setBounds(50, 50, 700, 30);
        mainPanel.add(messageLabel);

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        questionLabel.setForeground(Color.BLACK);
        questionLabel.setBounds(50, 100, 700, 30);
        questionLabel.setVisible(false);
        mainPanel.add(questionLabel);

        answerButtons = new JButton[4];
        setupAnswerButtons(mainPanel);

        add(mainPanel);

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

        SwingUtilities.invokeLater(() -> {
            this.setFocusable(true);
            this.requestFocusInWindow();
        });
    }

    private void handleMovement(KeyEvent e) {
        int moveAmount = 10;
        String direction = null;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> { // Move left
                tutorialPet.move(-moveAmount, 0);
                tutorialPet.walk();
                direction = "left";
            }
            case KeyEvent.VK_D -> { // Move right
                tutorialPet.move(moveAmount, 0);
                tutorialPet.walk();
                direction = "right";
            }
        }

        if (direction != null) {
            int count = movementCounts.get(direction) + 1;
            movementCounts.put(direction, count);
            checkMovementObjective();
        }
    }

    private void checkMovementObjective() {
        boolean leftCompleted = movementCounts.get("left") >= 2;
        boolean rightCompleted = movementCounts.get("right") >= 2;

        if (leftCompleted && rightCompleted && currentObjective == 1) {
            currentObjective++;
            showQuestionPromptDialog();
        }
    }

    private void showQuestionPromptDialog() {
        JOptionPane.showMessageDialog(
                this,
                "You're going to have to answer a question before doing any tasks like feeding, sleeping, or playing with your pet.\nAnswer correctly to proceed!",
                "Task Challenge",
                JOptionPane.INFORMATION_MESSAGE
        );
        startQuestionPhase();
    }

    private void startQuestionPhase() {
        messageLabel.setText("Objective 2: Answer the question below!");
        questionLabel.setForeground(Color.BLACK);
        questionLabel.setVisible(true);

        // Update question and answers
        String question = "2 + 2 = ?";
        String[] answers = {"3", "4", "5", "6"};
        updateQuestionUI(question, answers);
    }

    private void updateQuestionUI(String question, String[] answers) {
        questionLabel.setText(question);
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setText(answers[i]);
            answerButtons[i].setVisible(true);

            // Set button background colors
            switch (i) {
                case 0 -> answerButtons[i].setBackground(Color.RED);
                case 1 -> answerButtons[i].setBackground(Color.BLUE);
                case 2 -> answerButtons[i].setBackground(Color.YELLOW);
                case 3 -> answerButtons[i].setBackground(Color.GREEN);
            }

            // Add additional styling
            answerButtons[i].setForeground(Color.WHITE);
            answerButtons[i].setFont(new Font("Arial", Font.BOLD, 16));
        }
    }

    private void setupAnswerButtons(JPanel panel) {
        for (int i = 0; i < 4; i++) {
            JButton button = new JButton();
            button.setFont(new Font("Arial", Font.BOLD, 16));

            // Adjust positioning for 2 rows and 2 columns
            int buttonWidth = 150;
            int buttonHeight = 50;
            int horizontalSpacing = 20;
            int verticalSpacing = 20;

            int x = 300 + (i % 2) * (buttonWidth + horizontalSpacing); // Adjust for columns
            int y = 300 + (i / 2) * (buttonHeight + verticalSpacing); // Adjust for rows

            button.setBounds(x, y, buttonWidth, buttonHeight);
            button.setVisible(false); // Hidden initially
            button.addActionListener(new AnswerButtonListener(i));
            panel.add(button);
            answerButtons[i] = button;
        }
    }

    private class AnswerButtonListener implements ActionListener {
        private final int index;

        public AnswerButtonListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (index == 1) { // Correct answer index (e.g., "4")
                // Remove "Objective 2" text
                messageLabel.setText("");

                // Remove question and options
                questionLabel.setVisible(false);
                for (JButton button : answerButtons) {
                    button.setVisible(false);
                }

                // Show a dialog box about stats adjustment
                showStatsAdjustmentDialog(true);

                // Open the dialog box with icons
                showAllIconsDialog();

                // Show the food and treats dialog
                showFoodAndTreatDialog();

                // Automatically show the health warning dialog
                showHealthWarningDialog();
            } else {
                JOptionPane.showMessageDialog(TutorialGame.this, "Incorrect! Try Again.");
                // Show the stats adjustment dialog for incorrect answers
                showStatsAdjustmentDialog(false);
            }
        }
    }

    private void showStatsAdjustmentDialog(boolean correctAnswer) {
        String message;
        if (correctAnswer) {
            message = "Congratulations! Answering the question correctly increases your stats by 15, \n But be careful answering the question incorrectly decreases your stats by 20";
        } else {
            message = "Oh no! Answering the question incorrectly decreases your stats by 20.";
        }

        JOptionPane.showMessageDialog(
                this,
                message,
                "Stats Adjustment",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showAllIconsDialog() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10)); // 2 rows and 4 columns: icons on top, text below

        // Add health icon and description
        panel.add(createIconLabel("Assets/Images/healthicon.png"));
        panel.add(new JLabel("Health", SwingConstants.CENTER));

        // Add happiness icon and description
        panel.add(createIconLabel("Assets/Images/happyicon.png"));
        panel.add(new JLabel("Happiness", SwingConstants.CENTER));

        // Add sleep icon and description
        panel.add(createIconLabel("Assets/Images/sleepicon.png"));
        panel.add(new JLabel("Sleep", SwingConstants.CENTER));

        // Add hunger icon and description
        panel.add(createIconLabel("Assets/Images/foodicon.png"));
        panel.add(new JLabel("Hunger", SwingConstants.CENTER));

        // Display the dialog
        JOptionPane.showMessageDialog(
                this,
                panel,
                "Icons and Their Meanings",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private JLabel createIconLabel(String iconPath) {
        try {
            // Load the original image
            ImageIcon icon = new ImageIcon(ImageIO.read(new File(iconPath)));

            // Scale the image to a fixed size (50x50 pixels)
            Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
            return new JLabel("Error loading image");
        }
    }

    private void showFoodAndTreatDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical layout for clarity

        // Title and descriptions
        JLabel titleLabel = new JLabel("Food and Treats Available", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing

        JLabel descriptionLabel = new JLabel("Eating food increases hunger by 20. Eating treats increases happiness by 20.");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(descriptionLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing

        // Food section
        JLabel foodLabel = new JLabel("Food", SwingConstants.LEFT);
        foodLabel.setFont(new Font("Arial", Font.BOLD, 16));
        foodLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(foodLabel);

        JPanel foodPanel = new JPanel();
        foodPanel.setLayout(new BoxLayout(foodPanel, BoxLayout.X_AXIS)); // Horizontal layout for food
        foodPanel.add(createFoodAndTreatPanel("Assets/Images/Strawberry.png", ""));
        foodPanel.add(createFoodAndTreatPanel("Assets/Images/Orange.png", ""));
        foodPanel.add(createFoodAndTreatPanel("Assets/Images/banana.png", ""));
        foodPanel.add(createFoodAndTreatPanel("Assets/Images/Apple.png", ""));
        panel.add(foodPanel);

        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing

        // Treats section
        JLabel treatLabel = new JLabel("Treats", SwingConstants.LEFT);
        treatLabel.setFont(new Font("Arial", Font.BOLD, 16));
        treatLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(treatLabel);

        JPanel treatsPanel = new JPanel();
        treatsPanel.setLayout(new BoxLayout(treatsPanel, BoxLayout.X_AXIS)); // Horizontal layout for treats
        treatsPanel.add(createFoodAndTreatPanel("Assets/Images/Treat.png", ""));
        treatsPanel.add(createFoodAndTreatPanel("Assets/Images/Treat2.png", ""));
        panel.add(treatsPanel);

        // Display the dialog
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(500, 300)); // Adjust size as needed

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Food and Treats",
                JOptionPane.INFORMATION_MESSAGE
        );
    }



    private JPanel createFoodAndTreatPanel(String iconPath, String description) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS)); // Horizontal layout for each item
        itemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel iconLabel = createIconLabel(iconPath);
        JLabel descriptionLabel = new JLabel(description);

        iconLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10)); // Padding around the image
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        itemPanel.add(iconLabel);
        itemPanel.add(descriptionLabel);

        return itemPanel;
    }

    private void showHealthWarningDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical layout for clarity
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 15)); // Add padding around the dialog

        // Health warning message
        JPanel warningPanel = new JPanel();
        warningPanel.setLayout(new BoxLayout(warningPanel, BoxLayout.X_AXIS));
        JLabel healthIconLabel = createIconLabel("Assets/Images/healthicon.png");
        JLabel healthWarningLabel = new JLabel("If your health bar goes to zero, your pet dies!");
        healthWarningLabel.setFont(new Font("Arial", Font.BOLD, 14));
        healthWarningLabel.setForeground(Color.RED);

        warningPanel.add(healthIconLabel);
        warningPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add spacing between icon and text
        warningPanel.add(healthWarningLabel);
        warningPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Information about Vet and Sleep options
        JLabel vetOptionLabel = new JLabel("On the game page, there's an option to go to the vet, which increases health by 20.");
        JLabel sleepOptionLabel = new JLabel("There's also a sleep option, which increases health by 20.");
        vetOptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        sleepOptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        vetOptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sleepOptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Information about fun challenges
        JLabel challengeLabel = new JLabel("There are two fun challenges in the game:");
        JLabel question1Label = new JLabel("- Question 1: Solve it to get a chance for a reward.");
        JLabel question2Label = new JLabel("- Question 2: Solve it to get another chance for a reward.");
        JLabel rewardLabel = new JLabel("Successful completion of these challenges provides a randomized reward.");
        challengeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        question1Label.setFont(new Font("Arial", Font.PLAIN, 14));
        question2Label.setFont(new Font("Arial", Font.PLAIN, 14));
        rewardLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        challengeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        question1Label.setAlignmentX(Component.LEFT_ALIGNMENT);
        question2Label.setAlignmentX(Component.LEFT_ALIGNMENT);
        rewardLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add components to the main panel
        panel.add(warningPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing between sections
        panel.add(vetOptionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5))); // Small spacing
        panel.add(sleepOptionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Add spacing between health and challenge info
        panel.add(challengeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5))); // Small spacing
        panel.add(question1Label);
        panel.add(Box.createRigidArea(new Dimension(0, 5))); // Small spacing
        panel.add(question2Label);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Small spacing
        panel.add(rewardLabel);

        // Ensure proper size and visibility of the dialog
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(550, 300));

        // Display the dialog
        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Health and Game Features",
                JOptionPane.INFORMATION_MESSAGE
        );

        // End the tutorial here
        endTutorial();
    }

    private void endTutorial() {
        JOptionPane.showMessageDialog(
                this,
                "Tutorial completed! Returning to the main screen.",
                "Tutorial Complete",
                JOptionPane.INFORMATION_MESSAGE
        );
        dispose(); // Close the tutorial window
    }
}
