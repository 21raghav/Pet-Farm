package UI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.List;

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

    // List of pets
    private List<Pet> pets = new ArrayList<>();

    public ParentalControlsScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;

        // Set up the frame
        frame = new JFrame("Parental Controls");
        frame.setSize(1920, 1080); // Updated resolution to 1920x1080
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
        backgroundLabel = new JLabel(new ImageIcon(backgroundIcon.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH)));
        backgroundLabel.setBounds(0, 0, 1920, 1080);

        // Add password field
        passwordField = new JPasswordField(15);
        passwordField.setBounds(940, 590, 300, 80); // Adjusted bounds to match new resolution
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setOpaque(false);
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        frame.add(passwordField);

        // Add invisible button for "Enter Password"
        JButton invisibleLoginButton = new JButton();
        invisibleLoginButton.setBounds(750, 800, 400, 150); // Adjusted bounds to match new resolution
        invisibleLoginButton.setContentAreaFilled(false);
        invisibleLoginButton.setBorderPainted(false);
        frame.add(invisibleLoginButton);

        // Add ActionListener for the invisible button
        invisibleLoginButton.addActionListener(e -> handlePasswordSubmission());

        // Add sound effects
        ButtonUtils.addButtonClickSound(invisibleLoginButton, "Assets/Sounds/click.wav");
        ButtonUtils.addPasswordFieldEnterSound(passwordField, "Assets/Sounds/click.wav", this::handlePasswordSubmission);

        // Add status label
        statusLabel = new JLabel("");
        statusLabel.setForeground(Color.RED);
        statusLabel.setBounds(700, 580, 500, 30); // Adjusted bounds to match new resolution
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(statusLabel);

        // Add the background last
        frame.add(backgroundLabel);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set frame to fullscreen
        frame.setVisible(true);
        mainScreen.setVisible(false);
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

        endTimeField = new JTextField();
        endTimeField.setToolTipText("Enter the end time (e.g., 18:00 for 6 PM).");
        endTimeField.setBorder(BorderFactory.createTitledBorder("End Time (HH:MM)"));

        setRestrictionsButton = new JButton("Set Restrictions");

        restrictionsPanel.add(enableRestrictionsCheckBox);
        restrictionsPanel.add(new JLabel()); // Empty label for alignment
        restrictionsPanel.add(startTimeField);
        restrictionsPanel.add(endTimeField);
        restrictionsPanel.add(setRestrictionsButton);

        // Parental Statistics Section
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(3, 1, 10, 10));
        statsPanel.setBorder(new TitledBorder("Statistics"));

        playtimeLabel = new JLabel("Total Playtime: " + totalPlayTime + " hours");
        avgSessionLabel = new JLabel("Average Session Time: " + (totalPlayTime / sessionCount) + " hours");
        resetStatsButton = new JButton("Reset Statistics");

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
        controlPanel.setBounds(100, 100, 1700, 800); // Adjusted bounds to fit new resolution
        frame.add(controlPanel);

        // Refresh the frame
        frame.revalidate();
        frame.repaint();
    }

    private void reviveAllPets() {
        boolean anyRevived = false;
        for (Pet pet : pets) {
            if (pet.isDead) {
                pet.isDead = false;
                anyRevived = true;
            }
        }

        if (anyRevived) {
            JOptionPane.showMessageDialog(frame, "All dead pets have been revived!");
        } else {
            JOptionPane.showMessageDialog(frame, "No pets needed reviving.");
        }
    }

    private void launchGameAsParent() {
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
