package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameMenu extends JFrame {
    private Image backgroundImage;
    private Image dogImage;
    private Image statBarImage; // New variable for the stat bar image
    private Image healthIcon; // New variable for the health icon
    private Image inventoryIcon; // New variable for the inventory icon
    private Image inventoryImage; // New variable for inventory image
    private int dogX, dogY;  // Dog's position
    private int dogSpeedX = 10, dogSpeedY = 10;  // Dog's movement speed
    private int health = 100; // Health percentage
    private int happiness = 80; // Happiness percentage
    private int hunger = 30; // Hunger percentage
    private int sleep = 70; // Sleep percentage

    public GameMenu() {
        setTitle("Game Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Set the frame to full screen

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("Assets/GameImages/GameMenu.png"));
            dogImage = ImageIO.read(new File("Assets/Idle.png"));
            statBarImage = ImageIO.read(new File("Assets/Images/statbar.png")); // Load the stat bar image
            healthIcon = ImageIO.read(new File("Assets/Images/healthicon.png")); // Load the health icon image
            inventoryIcon = ImageIO.read(new File("Assets/Images/InventoryIcon.png")); // Load the inventory icon image
            inventoryImage = ImageIO.read(new File("Assets/Images/Inventory.png")); // Load the inventory image
            if (dogImage == null) {
                throw new IOException("Dog image could not be loaded.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading images: " + e.getMessage(), "Image Load Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw the background image
                g.drawImage(dogImage, dogX, dogY, this); // Draw the dog image
                
                // Draw the stat bar at the top right corner
                int statBarWidth = statBarImage.getWidth(null);
                int statBarHeight = statBarImage.getHeight(null);
                int statBarX = getWidth() - statBarWidth - 60;
                int statBarY = 10;

                // Draw health bar
                g.drawImage(statBarImage, statBarX, statBarY, this); // Positioning the health stat bar
                int healthFillWidth = (int)(statBarWidth * (health / 100.0)) - 4; // Subtracting for outline
                g.setColor(Color.RED);
                g.fillRect(statBarX + 2, statBarY + 2, healthFillWidth, statBarHeight - 4); // Fill health bar

                // Draw health icon next to the health bar
                g.drawImage(healthIcon, statBarX + statBarWidth + 5, statBarY, this); // Positioning the health icon

                // Draw happiness bar
                statBarY += statBarHeight + 10; // Move down for the happiness bar
                g.drawImage(statBarImage, statBarX, statBarY, this); // Positioning the happiness stat bar
                int happinessFillWidth = (int)(statBarWidth * (happiness / 100.0)) - 4; // Subtracting for outline
                g.setColor(Color.RED); // Different color for happiness
                g.fillRect(statBarX + 2, statBarY + 2, happinessFillWidth, statBarHeight - 4); // Fill happiness bar

                // Draw hunger bar
                statBarY += statBarHeight + 10; // Move down for the hunger bar
                g.drawImage(statBarImage, statBarX, statBarY, this); // Positioning the hunger stat bar
                int hungerFillWidth = (int)(statBarWidth * (hunger / 100.0)) - 4; // Subtracting for outline
                g.setColor(Color.RED); // Different color for hunger
                g.fillRect(statBarX + 2, statBarY + 2, hungerFillWidth, statBarHeight - 4); // Fill hunger bar

                // Draw sleep bar
                statBarY += statBarHeight + 10; // Move down for the sleep bar
                g.drawImage(statBarImage, statBarX, statBarY, this); // Positioning the sleep stat bar
                int sleepFillWidth = (int)(statBarWidth * (sleep / 100.0)) - 4; // Subtracting for outline
                g.setColor(Color.RED); // Different color for sleep
                g.fillRect(statBarX + 2, statBarY + 2, sleepFillWidth, statBarHeight - 4); // Fill sleep bar
            }
        };

        mainPanel.setLayout(null);
        setupKeyBindings(mainPanel); // Add this line to attach key bindings to the main panel
                
        add(mainPanel);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt) {
                dogX = (getWidth() - dogImage.getWidth(null)) / 2;
                dogY = (getHeight() - dogImage.getHeight(null)) /2;
            }
        });

        mainPanel.setFocusable(true);
        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                moveDog(e.getKeyCode());
                mainPanel.repaint();
            }
        });

        // Create Inventory Button with Icon
        JButton inventoryButton = new JButton(new ImageIcon(inventoryIcon));
        inventoryButton.setBounds(1565,300, inventoryIcon.getWidth(null), inventoryIcon.getHeight(null)); // Set button size to match the icon
        inventoryButton.setContentAreaFilled(false); // Make the button transparent
        inventoryButton.setBorderPainted(false);     // Remove the button border
        inventoryButton.setFocusPainted(false);     // Remove the focus outline
        inventoryButton.addActionListener(e -> showInventoryDialog());
        mainPanel.add(inventoryButton);

        setVisible(true);
    }

    private void setupKeyBindings(JPanel panel) {
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();
    
        inputMap.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
    
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveDog(KeyEvent.VK_UP);
                panel.repaint();
            }
        });
    
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveDog(KeyEvent.VK_DOWN);
                panel.repaint();
            }
        });
    
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveDog(KeyEvent.VK_LEFT);
                panel.repaint();
            }
        });
    
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveDog(KeyEvent.VK_RIGHT);
                panel.repaint();
            }
        });
    }
    
    private void moveDog(int keyCode) {
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        int heightBound = (getHeight() /2) - 150;
        int imageWidth = dogImage.getWidth(null);
        int imageHeight = dogImage.getHeight(null);
    
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                if (dogX - dogSpeedX >= 0) {
                    dogX -= dogSpeedX;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (dogX + dogSpeedX + imageWidth <= frameWidth) {
                    dogX += dogSpeedX;
                }
                break;
            case KeyEvent.VK_UP:
                if (dogY - dogSpeedY >= 0 && dogY >= heightBound) {
                    dogY -= dogSpeedY;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (dogY + dogSpeedY + imageHeight <= frameHeight-50) {
                    dogY += dogSpeedY;
                }
                break;
        }
    }
    
    private void showInventoryDialog() {
        JDialog inventoryDialog = new JDialog(this, "Inventory", false);
        inventoryDialog.setSize(525, 225);
        inventoryDialog.setLocationRelativeTo(this);
    
        JPanel inventoryPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (inventoryImage != null) {
                    g.drawImage(inventoryImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
    
        inventoryDialog.add(inventoryPanel);
        inventoryDialog.setVisible(true);
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameMenu::new);
    }
}