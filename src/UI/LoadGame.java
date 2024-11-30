package UI;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class LoadGame extends JFrame {
    public LoadGame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Create main panel with background image
        JPanel mainPanel = new JPanel() {
            @Override
            @SuppressWarnings("CallToPrintStackTrace")
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image backgroundImage = ImageIO.read(new File("Assets/GameImages/LoadGame.png"));
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mainPanel.setLayout(null);  // Using null layout for absolute positioning
        
        // Create a single button
        JButton slot1Btn = createButton();  // No need to pass position, handled in resize
        
        // Add button to panel
        mainPanel.add(slot1Btn);
        
        // Add panel to frame
        add(mainPanel);
        
        // Add component listener for resizing
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                resizeButton(slot1Btn);  // Adjust button size on resize
            }
        });
        
        setVisible(true);
    }
    
    private JButton createButton() {
        JButton button = new JButton();  // No text for the button
        button.setBounds(110, 390, 320, 240);  // Set position and size
        button.setFocusPainted(false);
        button.setBorderPainted(false);  // Show border for editing
        button.setContentAreaFilled(false);  // Make background transparent
        
        // Add action listener to navigate to GameMenu
        button.addActionListener(e -> { // Create an instance of GameMenu
            dispose();  // Close the current LoadGame window
        });

        return button;
    }
    
    private void resizeButton(JButton button) {
        // Example resizing logic; adjust as needed
        button.setBounds(button.getX(), button.getY(), button.getWidth(), button.getHeight());  // Keep the button size fixed
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoadGame::new);  // Run the LoadGame on the Event Dispatch Thread
    }
}
