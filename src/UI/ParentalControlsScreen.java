package UI;

import Game.DataManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParentalControlsScreen {
    private JFrame frame;
    private JLabel backgroundLabel;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    private JPanel controlPanel;
    private JCheckBox enableRestrictionsCheckBox;
    private JTextField startTimeField, endTimeField;
    private JLabel playtimeLabel, avgSessionLabel;
    private JButton setRestrictionsButton, resetStatsButton, revivePetButton, playGameButton;

    private MainScreen mainScreen;
    private int totalPlayTime = 0; // Placeholder for total playtime in hours
    private int sessionCount = 1;  // Placeholder for session count
    private static final String HARDCODED_PASSWORD = "myPassword";
    private Map<String,String> data;

    // List of pets
    private List<Pet> pets = new ArrayList<>();

    public ParentalControlsScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;

        this.data = DataManager.loadState("", "Restrictions.csv");

        // Set up the frame
        frame = new JFrame("Parental Controls");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                mainScreen.setVisible(true);
            }
        });

        // Set background image
        ImageIcon backgroundIcon = new ImageIcon("Assets/GameImages/ParentalPassword.png");
        backgroundLabel = new JLabel(new ImageIcon(backgroundIcon.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH)));
        backgroundLabel.setBounds(0, 0, screenSize.width, screenSize.height);

        // Add password field
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setOpaque(false);
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        passwordField.setBounds(screenSize.width / 2 - 10, screenSize.height / 2 , 300, 168);
        frame.add(passwordField);

        // Add invisible button for "Enter Password"
        JButton invisibleLoginButton = new JButton();
        invisibleLoginButton.setContentAreaFilled(false);
        invisibleLoginButton.setBorderPainted(false);
        invisibleLoginButton.setBounds(screenSize.width / 2 - 200, screenSize.height / 2 + 100, 400, 150);
        frame.add(invisibleLoginButton);

        // Add ActionListener for the invisible button
        invisibleLoginButton.addActionListener(e -> handlePasswordSubmission());

        // Add sound effects
        ButtonUtils.addButtonClickSound(invisibleLoginButton, "Assets/Sounds/click.wav");
        ButtonUtils.addPasswordFieldEnterSound(passwordField, "Assets/Sounds/click.wav", this::handlePasswordSubmission);

        // Add status label
        statusLabel = new JLabel("");
        statusLabel.setForeground(Color.RED);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setBounds(screenSize.width / 2 - 250, screenSize.height / 2 - 90, 500, 30);
        frame.add(statusLabel);

        // Add the background last
        frame.add(backgroundLabel);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set frame to fullscreen
        frame.setVisible(true);
        mainScreen.setVisible(false);

        setRestrictionsButton = new JButton("Set Restrictions");
        resetStatsButton = new JButton("Reset Statistics");

        setRestrictionsButton.addActionListener(e -> setPlaytimeRestrictions());
        resetStatsButton.addActionListener(e -> resetStatistics());
        revivePetButton.addActionListener(e -> reviveAllPets());
        playGameButton.addActionListener(e -> launchGameAsParent());
    }

    private void handlePasswordSubmission() {
        String password = new String(passwordField.getPassword());
        if (password.equals(HARDCODED_PASSWORD)) {
            showParentalControls();
        } else {
            statusLabel.setText("Incorrect password. Try again.");
        }
    }

    private void showParentalControls() {
        // Clear existing components
        frame.getContentPane().removeAll();

        // Create the control panel
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        // Parental Limitations Section
        JPanel restrictionsPanel = new JPanel();
        restrictionsPanel.setLayout(new GridLayout(3, 2, 10, 10));
        restrictionsPanel.setBorder(new TitledBorder("Playtime Restrictions"));

        enableRestrictionsCheckBox = new JCheckBox("Enable Restrictions");
        enableRestrictionsCheckBox.setToolTipText("Enable or disable playtime restrictions for your child.");

        startTimeField = new JTextField();
        startTimeField.setToolTipText("Enter the start time (e.g., 09:00 for 9 AM).");
        startTimeField.setBorder(BorderFactory.createTitledBorder("Start Time (HH:MM)"));
        startTimeField.setText(this.data.get("StartTime"));

        endTimeField = new JTextField();
        endTimeField.setToolTipText("Enter the end time (e.g., 18:00 for 6 PM).");
        endTimeField.setBorder(BorderFactory.createTitledBorder("End Time (HH:MM)"));
        endTimeField.setText(this.data.get("EndTime"));

        restrictionsPanel.add(enableRestrictionsCheckBox);
        restrictionsPanel.add(new JLabel()); // Empty label for alignment
        restrictionsPanel.add(startTimeField);
        restrictionsPanel.add(endTimeField);
        restrictionsPanel.add(setRestrictionsButton);

        // Parental Statistics Section
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(3, 1, 10, 10));
        statsPanel.setBorder(new TitledBorder("Statistics"));

        playtimeLabel = new JLabel("Total Playtime: " + this.data.get("Total") + " hours");
        avgSessionLabel = new JLabel("Average Session Time: " + this.data.get("Average") + " hours");

        statsPanel.add(playtimeLabel);
        statsPanel.add(avgSessionLabel);
        statsPanel.add(resetStatsButton);


        // Revive Pet Section
        JPanel revivePanel = new JPanel();
        revivePanel.setBorder(new TitledBorder("Pet Revival"));
        revivePetButton = new JButton("Revive All Pets");
        revivePetButton.addActionListener(e -> reviveAllPets());
        revivePanel.add(revivePetButton);

        // Play Game as Parent Section
        JPanel playGamePanel = new JPanel();
        playGamePanel.setBorder(new TitledBorder("Play Game"));
        playGameButton = new JButton("Play Game as Parent");
        playGameButton.addActionListener(e -> launchGameAsParent());
        playGamePanel.add(playGameButton);

        // Add panels to the control panel
        controlPanel.add(restrictionsPanel);
        controlPanel.add(statsPanel);
        controlPanel.add(revivePanel);
        controlPanel.add(playGamePanel);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> {
            frame.dispose();
            mainScreen.setVisible(true);
        });
        controlPanel.add(backButton);

        // Add control panel to the frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        controlPanel.setBounds(screenSize.width / 10, screenSize.height / 10, screenSize.width * 8 / 10, screenSize.height * 8 / 10);
        frame.add(controlPanel);

        // Refresh the frame
        frame.revalidate();
        frame.repaint();
    }

    public void setPlaytimeRestrictions() {
        System.out.println("pressed");
        if (enableRestrictionsCheckBox.isSelected()) {
            String startTime = startTimeField.getText();
            String endTime = endTimeField.getText();
            this.data.put("StartTime", startTime);
            this.data.put("EndTime", endTime);
            DataManager.saveState("restrictions.csv", this.data);
            // Implement logic to set playtime restrictions
            JOptionPane.showMessageDialog(frame, "Playtime restrictions set from " + startTime + " to " + endTime);
        } else {
            this.data.put("StartTime", "00:00");
            this.data.put("EndTime", "23:59");

            DataManager.saveState("restrictions.csv", this.data);
            JOptionPane.showMessageDialog(frame, "Playtime restrictions disabled.");
        }
    }

    private void resetStatistics() {
        // Implement logic to reset statistics
        totalPlayTime = 0;
        sessionCount = 0;
        playtimeLabel.setText("Total Playtime: " + totalPlayTime + " hours");
        avgSessionLabel.setText("Average Session Time: " + (totalPlayTime / sessionCount) + " hours");
        JOptionPane.showMessageDialog(frame, "Statistics have been reset.");

        this.data.put("Total", String.valueOf(totalPlayTime));
        this.data.put("Average", String.valueOf(totalPlayTime / sessionCount));
        this.data.put("Times", String.valueOf(sessionCount));

        DataManager.saveState("restrictions.csv", this.data);
    }

    private void reviveAllPets() {
        for(int i = 1; i <= 4; i ++) {
            String fileName = "slot" + i + ".csv";
            Map<String,String> map = DataManager.loadState("", fileName);

            int health = Integer.parseInt(map.get("health"));
            if (health == 0) {
                map.put("health", "50");
            }
            DataManager.saveState(fileName, map);
        }

        JOptionPane.showMessageDialog(frame, "All dead pets have been revived!");
    }


    private void launchGameAsParent() {
        mainScreen.changeImage("Assets/GameImages/LoadGame.png", "Load Game Menu");
        JOptionPane.showMessageDialog(frame, "Game launched for parent!");
    }

    static class Pet {
        String name;
        boolean isDead;

        Pet(String name, boolean isDead) {
            this.name = name;
            this.isDead = isDead;
        }
    }
}
