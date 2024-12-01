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
    private Map<String, Integer> correctAnswerIndexMap; // Map to hold the index of the correct answer for each question
    private String currentQuestion;
    private final JButton[] answerButtons;
    private final JLabel questionLabel;
    private Inventory inventory;
    private Random random;
    private int type;
    private statistics stats;

    public Questions(Inventory inventory, statistics stats, int type) {
        this.inventory = inventory;
        setTitle("Questions");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Changed to ensure windowClosed events are fired
        this.inventory = inventory;
        this.type = type;
        this.stats = stats;
        random = new Random();  // Create a Random object for index generation
        
        initializeQuestions();
        selectRandomQuestion();
        
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(null);
        
        questionLabel = new JLabel();
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 40));
        questionLabel.setBounds(300, 100, 200, 50);
        mainPanel.add(questionLabel);
        
        answerButtons = new JButton[4]; // Assuming 4 answers per question
        setupAnswerButtons(mainPanel);
        
        updateQuestionUI();
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void setupAnswerButtons(JPanel panel) {
        Color[] colors = {Color.RED, Color.BLUE, Color.MAGENTA, Color.GREEN};
        for (int i = 0; i < 4; i++) {
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
        correctAnswerIndexMap = new HashMap<>();

        // Add questions and their correct answers
        questions.add("2 + 7 = ?");
        answersMap.put("2 + 7 = ?", new String[]{"9", "11", "6", "5"});
        correctAnswerIndexMap.put("2 + 7 = ?", 0);

        questions.add("3 * 4 = ?");
        answersMap.put("3 * 4 = ?", new String[]{"15", "7", "10", "12"});
        correctAnswerIndexMap.put("3 * 4 = ?", 3);

        questions.add("5 - 2 = ?");
        answersMap.put("5 - 2 = ?", new String[]{"2", "3", "4", "1"});
        correctAnswerIndexMap.put("5 - 2 = ?", 1);
    }

    private void selectRandomQuestion() {
        Random rand = new Random();
        currentQuestion = questions.get(rand.nextInt(questions.size()));
    }

    private class AnswerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            int selectedIndex = Integer.parseInt(clickedButton.getActionCommand());
            if (correctAnswerIndexMap.get(currentQuestion) == selectedIndex) 
            {
                JOptionPane.showMessageDialog(Questions.this, "Correct!");
                if(type == 1){
                    int randomIndex = random.nextInt(6);  // Generate a random index between 0 and 5
                    inventory.updateItemCount(randomIndex, 1);  // Assuming you want to increase the first item in the inventory

                }
                if(type == 2){
                    stats.updateState(3, 20);
                }
                if(type == 3) {
                    stats.updateState(0, 20);
                    
                }
            }
            else {
                JOptionPane.showMessageDialog(Questions.this, "Incorrect!");
                if(type == 1){
                    stats.updateState(1, -5);
                    stats.updateState(2, -5);
                }
                if(type == 2){
                    stats.updateState(3, -5);
                }
                if(type == 3){
                    stats.updateState(0, -5);
                }
            }
            dispose();  // Close the questions window
        }
    }
}
