package UI;

import java.awt.*;
import javax.swing.*;

public class Inventory{
    private JDialog inventoryDialog;
    private final int[] itemCounts = {5, 3, 2, 4, 1, 6}; // Example counts for each item
    private final Image inventoryImage;
    private final JButton inventoryButton;
    private final GameMenu gameMenu;
    private final statistics gameStats; // Reference to the statistics instance

    // Update the statIndices array to reflect the new mapping
    private final int[] statIndices = {0, 0, 0, 0, 1, 1}; // 0-3 increase hunger, 4-5 increase happiness

    public Inventory(GameMenu gameMenu, statistics gameStats, Image inventoryImage, JButton inventoryButton) {
        this.gameMenu = gameMenu;
        this.gameStats = gameStats; // Initialize the statistics reference
        this.inventoryImage = inventoryImage;
        this.inventoryButton = inventoryButton;
        setupInventoryDialog();
    }

    private void setupInventoryDialog() {
        inventoryDialog = new JDialog(gameMenu, "Inventory", false);
        inventoryDialog.setSize(525, 225);
        inventoryDialog.setLocationRelativeTo(gameMenu);
        inventoryDialog.setResizable(false);
        JPanel inventoryPanel = createInventoryPanel();
        inventoryDialog.add(inventoryPanel);
        inventoryDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                inventoryButton.setEnabled(true); // Re-enable the inventory button
            }
        });
    }

    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (inventoryImage != null) {
                    g.drawImage(inventoryImage, 0, 0, getWidth(), getHeight(), this);
                }
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.setColor(Color.BLACK);
                for (int i = 0; i < itemCounts.length; i++) {
                    g.drawString(String.valueOf(itemCounts[i]), 60 + 86 * i, 120);
                }
            }
        };
        panel.setLayout(null);
        addButton(panel);
        return panel;
    }

    private void addButton(JPanel panel) {
        for (int i = 0; i < itemCounts.length; i++) {
            int index = i; // Capture the index for use in the lambda
            JButton button = new JButton();
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setBounds(85 * i, 90, 85, 100);
            button.addActionListener(e -> {
                if (itemCounts[index] > 0) {
                    itemCounts[index]--; // Decrement the item count
                    increaseStat(statIndices[index], 10); // Call to increaseStat
                } else {
                    JOptionPane.showMessageDialog(inventoryDialog, "No more items left!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            });
            panel.add(button);
        }
    }

    // Method to increase the stat based on the index
    private void increaseStat(int statIndex, int increment) {
        // Call the updateStat method on the statistics instance
        gameStats.updateStat(statIndex, increment);
    }

    public void toggleInventoryDisplay() {
        if (!inventoryDialog.isVisible()) {
            inventoryDialog.setVisible(true);
            inventoryButton.setEnabled(false);
        }
    }

    public void updateItemCount(int index, int count) {
        if (index >= 0 && index < itemCounts.length) {
            itemCounts[index] = count;
            inventoryDialog.repaint(); // Refresh the dialog to show updated counts
        }
    }
}

