package UI;

import Pets.Pet;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;

public class VetShelter {
    private final JPanel backgroundPanel; // Background panel
    private statistics stats; // Statistics instance
    GameMenu gameMenu;
    private Inventory inventory;
    public VetShelter(Pet animal, statistics stats) {

        JFrame frame = new JFrame("Vet Shelter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameMenu = gameMenu;
        this.inventory = inventory;
        this.stats = stats;

        backgroundPanel = new JPanel() {
            private final Image backgroundImage = new ImageIcon("Assets/GameImages/vetshelter.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                stats.drawStats(g, getWidth(), getHeight()); // Draw the statistics
            }
        };

        // Set the layout manager to null for absolute positioning of components
        backgroundPanel.setLayout(null);

        ImageIcon question = new ImageIcon("Assets/Images/question.png");
        JButton questionButton = new JButton(question);
        questionButton.setBounds(800, 150, question.getIconWidth(), question.getIconHeight()); // Adjust positioning as needed
        questionButton.setBorderPainted(false); // Removes the border around the button
        questionButton.setContentAreaFilled(false); // Ensures the background inside the button is not filled
        questionButton.setFocusPainted(false); // Removes the focus indicator when the button is clicked
        questionButton.setOpaque(false); // Makes sure the button is transparent
        // Position the button at the top-right corner
        backgroundPanel.add(questionButton);

        questionButton.addActionListener(e -> {
        questionButton.setEnabled(false); // Disable the button to prevent re-clicks
        Questions questionsWindow = new Questions(inventory, stats, 3); // Open the Questions window !! TYPE 3 = VET
        
        questionsWindow.addWindowListener((WindowListener) new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                questionButton.setEnabled(true); // Re-enable the button once the window is closed
                backgroundPanel.repaint();  // Trigger repaint whenever stats are updated
                }
            });
        });   

        // Create an exit button
        ImageIcon exit = new ImageIcon("Assets/Images/goBack.png");
        JButton exitButton = new JButton(exit);
        exitButton.setBorderPainted(false); // Removes the border around the button
        exitButton.setContentAreaFilled(false); // Ensures the background inside the button is not filled
        exitButton.setFocusPainted(false); // Removes the focus indicator when the button is clicked
        exitButton.setOpaque(false); // Makes sure the button is transparent
        // Position the button at the top-right corner
        exitButton.setBounds(10, 10, exit.getIconWidth(), exit.getIconHeight()); // Adjusted positioning
        // Add the exit button directly to the background panel
        backgroundPanel.add(exitButton);
        
        exitButton.addActionListener(e -> {
            frame.dispose();
            new GameMenu(animal);
        });

        JPanel character = animal.getAnimationPanel();
        animal.sleep();
        character.setBounds(100, 100, character.getPreferredSize().width, character.getPreferredSize().height);
        backgroundPanel.addKeyListener(new KeyboardListener(animal));
        backgroundPanel.add(character);

        backgroundPanel.setFocusable(true);
        backgroundPanel.requestFocusInWindow();
        frame.setVisible(true);

        frame.getContentPane().add(backgroundPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
