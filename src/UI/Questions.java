package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.*;

public class Questions extends JFrame {
    private Image backgroundImage;
    private List<String> questions;
    private Map<String, String[]> answersMap;
    private String currentQuestion;
    private final JButton[] answerButtons;
    private final JLabel questionLabel;
    private Inventory inventory;

    public Questions(Inventory inventory) {
        setTitle("Questions");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    
        // Initialize data before UI components
        initializeQuestions();
        selectRandomQuestion();

        this.inventory = inventory;
    
        // Create main panel with custom painting
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(null);
    
        // Setup and add question label
        questionLabel = new JLabel();
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 40));
        questionLabel.setBounds(300, 100, 200, 50);
        mainPanel.add(questionLabel);
    
        // Setup and add answer buttons
        answerButtons = new JButton[4]; // Assuming 4 answers per question
        setupAnswerButtons(mainPanel);
    
        // Initial UI update
        updateQuestionUI();
    
        add(mainPanel);
        setVisible(true);
    }
    
    private void setupAnswerButtons(JPanel panel) {
        Color[] colors = {Color.RED, Color.BLUE, Color.MAGENTA, Color.GREEN};
        for (int i = 0; i < 4; i++) { // Assuming 4 answers per question
            JButton button = new JButton();
            button.setBackground(colors[i]);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 36));
            button.setFocusPainted(false);
            button.setBounds((i % 2) * 400, 285 + (i / 2) * 160, 400, 160);
            button.setActionCommand(String.valueOf(i));
            button.addActionListener(new AnswerButtonListener());
            panel.add(button);
            answerButtons[i] = button;
        }
    }

    private void updateQuestionUI() {
        questionLabel.setText(currentQuestion);
        String[] answers = answersMap.get(currentQuestion);
        if (answers != null && answers.length == answerButtons.length) {
            for (int i = 0; i < answerButtons.length; i++) {
                answerButtons[i].setText(answers[i]);
            }
        }
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();
        answersMap = new HashMap<>();
        
        // Hardcoded correct answers
        String[] correctAnswers = {"9", "12", "3"}; // Correct answers for the questions

        // Add questions and their corresponding answers
        questions.add("2 + 7 = ?");
        answersMap.put("2 + 7 = ?", new String[]{"9", "11", "6", "5"});

        questions.add("3 * 4 = ?");
        answersMap.put("3 * 4 = ?", new String[]{"15", "7", "10", "12"}); 

        questions.add("5 - 2 = ?");
        answersMap.put("5 - 2 = ?", new String[]{"2", "3", "4", "1"});
    }

    private void selectRandomQuestion() {
        try {
            Random rand = new Random();
            currentQuestion = questions.get(rand.nextInt(questions.size()));
        } catch (Exception e) {
            System.err.println("Error selecting a random question: " + e.getMessage());
        }
    }

    private class AnswerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            String selectedAnswer = clickedButton.getText();
            int selectedIndex = Integer.parseInt(clickedButton.getActionCommand());
            String[] correctAnswers = answersMap.get(currentQuestion);
            if (correctAnswers != null && selectedAnswer.equals(correctAnswers[selectedIndex])) {
                JOptionPane.showMessageDialog(Questions.this, "Correct!");
                inventory.increaseRandomInventoryItem();  // Call to increase random inventory item
                

            } else {
                JOptionPane.showMessageDialog(Questions.this, "Incorrect!");

            }
            dispose();
        }
    }         
}