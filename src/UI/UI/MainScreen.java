package UI;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;

public class MainScreen extends JFrame {
    private JPanel currentPanel;
    private ImageIcon currentBackground;
    
    // Update these constants for button positioning (as percentages)
    private final double LOAD_BUTTON_X_PERCENT = 0.45; 
    private final double LOAD_BUTTON_Y_PERCENT = 0.417; 
    private final double BUTTON_WIDTH_PERCENT = 0.14;  
    private final double BUTTON_HEIGHT_PERCENT = 0.1; 
    private final double NEW_BUTTON_Y_PERCENT = 0.59; 
    
    public MainScreen() {
        System.out.println("Initializing MainScreen..."); // Debug print
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start maximized
        
        // Add a component listener to handle window resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateButtonPositions();
            }
        });
        
        // Try to load the main menu image first to verify path
        File imageFile = new File("Assets/GameImages/MainMenu.png");
        if (!imageFile.exists()) {
            System.err.println("Cannot find file: " + imageFile.getAbsolutePath());
            // Try to list contents of Assets directory to debug
            File assetsDir = new File("Assets");
            if (assetsDir.exists()) {
                System.out.println("Contents of Assets directory:");
                for (File file : assetsDir.listFiles()) {
                    System.out.println(file.getPath());
                }
            } else {
                System.err.println("Assets directory not found!");
            }
        }
        
        showMainMenu();
        setVisible(true); // Make sure to set visible
    }
    
    private void showMainMenu() {
        System.out.println("Showing main menu..."); // Debug print
        if (currentPanel != null) {
            remove(currentPanel);
        }
        
        currentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    ImageIcon background = new ImageIcon("Assets/GameImages/MainMenu.png");
                    if (background.getImageLoadStatus() != MediaTracker.COMPLETE) {
                        System.err.println("Failed to load MainMenu.png");
                        return;
                    }
                    g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    System.err.println("Error loading/drawing background: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        
        currentPanel.setLayout(null);
        
        // Create buttons with initial positions
        JButton loadGameBtn = createTransparentButton(0, 0, 0, 0); // Size will be set in updateButtonPositions
        
        loadGameBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loadGameBtn.setBorderPainted(false);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                loadGameBtn.setBorderPainted(false);
            }
        });
        
        loadGameBtn.addActionListener(e -> showLoadGame());
        
        currentPanel.add(loadGameBtn);
        
        add(currentPanel);
        
        // Set initial button positions
        updateButtonPositions();
        
        revalidate();
        repaint();
    }
    
    private void updateButtonPositions() {
        // Get current window dimensions
        int width = getContentPane().getWidth();
        int height = getContentPane().getHeight();
        
        // Calculate button dimensions based on percentages
        int buttonWidth = (int)(width * BUTTON_WIDTH_PERCENT);
        int buttonHeight = (int)(height * BUTTON_HEIGHT_PERCENT);
        int loadButtonX = (int)(width * LOAD_BUTTON_X_PERCENT);
        int loadButtonY = (int)(height * LOAD_BUTTON_Y_PERCENT);
        int newButtonY = (int)(height * NEW_BUTTON_Y_PERCENT);
        
        // Update all buttons in the current panel
        for (Component c : currentPanel.getComponents()) {
            if (c instanceof JButton) {
                JButton button = (JButton)c;
                if (button.getActionListeners()[0].toString().contains("showLoadGame")) {
                    button.setBounds(loadButtonX, loadButtonY, buttonWidth, buttonHeight);
                } else {
                    button.setBounds(loadButtonX, newButtonY, buttonWidth, buttonHeight);
                }
            }
        }
    }
    
    private void showLoadGame() {
        // Close the current window
        this.dispose();
        
        // Create and show the LoadGame window
        SwingUtilities.invokeLater(() -> {
            LoadGame loadGame = new LoadGame();
            loadGame.setVisible(true);
        });
    }
    
    private JButton createTransparentButton(int x, int y, int width, int height) {
        JButton button = new JButton();
        button.setBounds(x, y, width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        
        // Optional: Make button visible on hover for debugging
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBorderPainted(true);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBorderPainted(false);
            }
        });
        
        return button;
    }
}