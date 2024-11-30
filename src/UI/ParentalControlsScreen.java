package UI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List; // Correct List import
import java.util.Timer;

public class ParentalControlsScreen {
    private JFrame frame;
    private JCheckBox enableRestrictionsCheckBox;
    private JTextField startTimeField, endTimeField;
    private JLabel playtimeLabel, avgSessionLabel, statusLabel;
    private JButton setRestrictionsButton, resetStatsButton, revivePetButton, playGameAsParentButton;
    private MainScreen mainScreen;

    private boolean restrictionsEnabled = false;
    private LocalTime startTime, endTime;
    private int totalPlayTime = 0;  // In minutes
    private int sessionCount = 1;
    private Timer playtimeTimer;

    private List<Pet> pets = new ArrayList<>(); // Use java.util.List here

    private static final String HARDCODED_PASSWORD = "parent123";
    private static final String SAVE_FILE = "parental_controls.json";

    public ParentalControlsScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;

        // Load saved settings
        loadParentalSettings();

        // Set up the frame
        frame = new JFrame("Parental Controls");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        showPasswordScreen();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Fullscreen
        frame.setVisible(true);
        mainScreen.setVisible(false);
    }

    private void showPasswordScreen() {
        frame.getContentPane().removeAll();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        JLabel label = new JLabel("Enter Password:");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        submitButton.addActionListener(e -> {
            String password = new String(passwordField.getPassword());
            if (password.equals(HARDCODED_PASSWORD)) {
                showParentalControls();
            } else {
                JOptionPane.showMessageDialog(frame, "Incorrect password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(label);
        panel.add(Box.createVerticalStrut(20));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(20));
        panel.add(submitButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void showParentalControls() {
        frame.getContentPane().removeAll();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel restrictionsPanel = createRestrictionsPanel();
        JPanel statsPanel = createStatsPanel();
        JPanel revivePanel = createRevivePanel();
        JPanel playGamePanel = createPlayGamePanel(); // Add Play Game as Parent section

        mainPanel.add(restrictionsPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(statsPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(revivePanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(playGamePanel); // Add Play Game as Parent section

        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.PLAIN, 18));
        backButton.addActionListener(e -> {
            saveParentalSettings();
            frame.dispose();
            mainScreen.setVisible(true);
        });

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(backButton);

        frame.add(new JScrollPane(mainPanel), BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private JPanel createRestrictionsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(new TitledBorder("Playtime Restrictions"));

        enableRestrictionsCheckBox = new JCheckBox("Enable Restrictions");
        enableRestrictionsCheckBox.setSelected(restrictionsEnabled);

        startTimeField = new JTextField(startTime != null ? startTime.toString() : "HH:MM");
        endTimeField = new JTextField(endTime != null ? endTime.toString() : "HH:MM");

        setRestrictionsButton = new JButton("Set Restrictions");
        setRestrictionsButton.addActionListener(e -> {
            restrictionsEnabled = enableRestrictionsCheckBox.isSelected();
            try {
                startTime = LocalTime.parse(startTimeField.getText());
                endTime = LocalTime.parse(endTimeField.getText());
                JOptionPane.showMessageDialog(frame, "Restrictions updated successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid time format. Use HH:MM.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(enableRestrictionsCheckBox);
        panel.add(new JLabel());  // Spacer
        panel.add(startTimeField);
        panel.add(endTimeField);
        panel.add(setRestrictionsButton);

        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(new TitledBorder("Parental Statistics"));

        playtimeLabel = new JLabel("Total Playtime: " + totalPlayTime + " minutes");
        avgSessionLabel = new JLabel("Average Session Time: " + (totalPlayTime / sessionCount) + " minutes");

        resetStatsButton = new JButton("Reset Statistics");
        resetStatsButton.addActionListener(e -> {
            totalPlayTime = 0;
            sessionCount = 1;
            updateStats();
        });

        panel.add(playtimeLabel);
        panel.add(avgSessionLabel);
        panel.add(resetStatsButton);

        return panel;
    }

    private JPanel createRevivePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Revive Pet"));

        revivePetButton = new JButton("Revive All Pets");
        revivePetButton.addActionListener(e -> {
            for (Pet pet : pets) {
                pet.revive();
            }
            JOptionPane.showMessageDialog(frame, "All pets have been revived.");
        });

        panel.add(revivePetButton);
        return panel;
    }

    private JPanel createPlayGamePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Play Game as Parent"));

        playGameAsParentButton = new JButton("Play Game as Parent");
        playGameAsParentButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Game launched in parent mode!");
            // Add logic to launch the game for the parent user here
        });

        panel.add(playGameAsParentButton);
        return panel;
    }

    private void updateStats() {
        playtimeLabel.setText("Total Playtime: " + totalPlayTime + " minutes");
        avgSessionLabel.setText("Average Session Time: " + (totalPlayTime / sessionCount) + " minutes");
    }

    private void saveParentalSettings() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAVE_FILE))) {
            writer.write(String.format("{\"restrictionsEnabled\":%b,\"startTime\":\"%s\",\"endTime\":\"%s\",\"totalPlayTime\":%d,\"sessionCount\":%d}",
                    restrictionsEnabled, startTime, endTime, totalPlayTime, sessionCount));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadParentalSettings() {
        try {
            String json = Files.readString(Paths.get(SAVE_FILE));
            // Parse JSON (use a library like Gson if preferred)
        } catch (IOException e) {
            // Defaults if no file exists
        }
    }

    static class Pet {
        boolean isDead;

        void revive() {
            isDead = false;
        }
    }
}
