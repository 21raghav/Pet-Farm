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

        // Question 4
        questions.add("10 / 2 = ?");
        answersMap.put("10 / 2 = ?", new String[]{"2", "5", "8", "10"});
        correctAnswerIndexMap.put("10 / 2 = ?", 1);

        // Question 5
        questions.add("6 + 4 = ?");
        answersMap.put("6 + 4 = ?", new String[]{"9", "12", "10", "8"});
        correctAnswerIndexMap.put("6 + 4 = ?", 2);

        // Question 6
        questions.add("9 - 3 = ?");
        answersMap.put("9 - 3 = ?", new String[]{"6", "5", "4", "3"});
        correctAnswerIndexMap.put("9 - 3 = ?", 0);

        // Question 7
        questions.add("7 * 2 = ?");
        answersMap.put("7 * 2 = ?", new String[]{"12", "14", "16", "10"});
        correctAnswerIndexMap.put("7 * 2 = ?", 1);

        // Question 8
        questions.add("8 + 5 = ?");
        answersMap.put("8 + 5 = ?", new String[]{"12", "14", "13", "11"});
        correctAnswerIndexMap.put("8 + 5 = ?", 2);

        // Question 9
        questions.add("15 / 3 = ?");
        answersMap.put("15 / 3 = ?", new String[]{"5", "3", "6", "4"});
        correctAnswerIndexMap.put("15 / 3 = ?", 0);

        // Question 10
        questions.add("12 - 7 = ?");
        answersMap.put("12 - 7 = ?", new String[]{"4", "5", "6", "3"});
        correctAnswerIndexMap.put("12 - 7 = ?", 1);

        // Question 11
        questions.add("4 + 6 = ?");
        answersMap.put("4 + 6 = ?", new String[]{"8", "9", "10", "11"});
        correctAnswerIndexMap.put("4 + 6 = ?", 2);

        // Question 12
        questions.add("8 - 5 = ?");
        answersMap.put("8 - 5 = ?", new String[]{"3", "4", "5", "2"});
        correctAnswerIndexMap.put("8 - 5 = ?", 0);

        // Question 13
        questions.add("6 * 3 = ?");
        answersMap.put("6 * 3 = ?", new String[]{"18", "16", "20", "15"});
        correctAnswerIndexMap.put("6 * 3 = ?", 0);

        // Question 14
        questions.add("20 / 4 = ?");
        answersMap.put("20 / 4 = ?", new String[]{"5", "4", "6", "3"});
        correctAnswerIndexMap.put("20 / 4 = ?", 0);

        // Question 15
        questions.add("11 + 7 = ?");
        answersMap.put("11 + 7 = ?", new String[]{"16", "17", "18", "19"});
        correctAnswerIndexMap.put("11 + 7 = ?", 2);

        // Question 16
        questions.add("14 - 6 = ?");
        answersMap.put("14 - 6 = ?", new String[]{"9", "8", "7", "6"});
        correctAnswerIndexMap.put("14 - 6 = ?", 1);

        // Question 17
        questions.add("5 * 5 = ?");
        answersMap.put("5 * 5 = ?", new String[]{"20", "25", "30", "15"});
        correctAnswerIndexMap.put("5 * 5 = ?", 1);

        // Question 18
        questions.add("16 / 2 = ?");
        answersMap.put("16 / 2 = ?", new String[]{"8", "6", "9", "7"});
        correctAnswerIndexMap.put("16 / 2 = ?", 0);

        // Question 19
        questions.add("9 + 3 = ?");
        answersMap.put("9 + 3 = ?", new String[]{"11", "13", "12", "14"});
        correctAnswerIndexMap.put("9 + 3 = ?", 2);

        // Question 20
        questions.add("18 - 7 = ?");
        answersMap.put("18 - 7 = ?", new String[]{"12", "11", "10", "9"});
        correctAnswerIndexMap.put("18 - 7 = ?", 1);
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
                    stats.updateState(3, 15);
                }
                if(type == 3) {
                    stats.updateState(0, 15);
                    
                }
            }
            else {
                JOptionPane.showMessageDialog(Questions.this, "Incorrect!");
                if(type == 1){
                    stats.updateState(1, -15);
                    stats.updateState(2, -20);
                }
                if(type == 2){
                    stats.updateState(3, -25);
                }
                if(type == 3){
                    stats.updateState(0, -20);
                }
            }
            dispose();  // Close the questions window
        }
    }
}
