package UI;

import Game.DataManager;

import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class Inventory{
    private JDialog inventoryDialog;
    private final int[] itemCounts = {0,0,0,0,0,0}; // Example counts for each item
    private final Map<String, String> data;
    private final Image inventoryImage;
    private final JButton inventoryButton;
    private final GameMenu gameMenu;
    private final statistics gameStats; // Reference to the statistics instance
    private String saveFileName;

    // Update the statIndices array to reflect the new mapping
    private final int[] statIndices = {2, 2, 2, 2, 1, 1}; // 0-3 increase hunger, 4-5 increase happiness

    public Inventory(String saveFileName, GameMenu gameMenu, statistics gameStats, Image inventoryImage, JButton inventoryButton, Map<String, String> data) {
        this.gameMenu = gameMenu;
        this.gameStats = gameStats; // Initialize the statistics reference
        this.inventoryImage = inventoryImage;
        this.inventoryButton = inventoryButton;
        this.saveFileName = saveFileName;
        setupInventoryDialog();

        // Interact with csv
        this.data = data;
        itemCounts[0] = Integer.parseInt(data.get("apple"));
        itemCounts[1] = Integer.parseInt(data.get("orange"));
        itemCounts[2] = Integer.parseInt(data.get("strawberry"));
        itemCounts[3] = Integer.parseInt(data.get("banana"));
        itemCounts[4] = Integer.parseInt(data.get("ybone"));
        itemCounts[5] = Integer.parseInt(data.get("bbone"));

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

                    switch(index) {
                        case 0:
                            data.put("apple", String.valueOf(Integer.parseInt(data.get("apple")) - 1));
                            break;
                        case 1:
                            data.put("orange", String.valueOf(Integer.parseInt(data.get("orange")) - 1));
                            break;
                        case 2:
                            data.put("strawberry", String.valueOf(Integer.parseInt(data.get("strawberry")) - 1));
                            break;
                        case 3:
                            data.put("banana", String.valueOf(Integer.parseInt(data.get("banana")) - 1));
                            break;
                        case 4:
                            data.put("ybone", String.valueOf(Integer.parseInt(data.get("ybone")) - 1));
                            break;
                        case 5:
                            data.put("bbone", String.valueOf(Integer.parseInt(data.get("bbone")) - 1));
                            break;
                    }

                    DataManager.saveState(this.saveFileName, this.data);
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
        gameStats.updateState(statIndex, increment);
    }

    public void toggleInventoryDisplay() {
        if (!inventoryDialog.isVisible()) {
            inventoryDialog.setVisible(true);
            inventoryButton.setEnabled(false);
        }
    }

    public void updateItemCount(int index, int count) {
        if (index >= 0 && index < itemCounts.length) {
            itemCounts[index] += count;
            inventoryDialog.repaint(); // Refresh the dialog to show updated counts
            switch(index) {
                case 0:
                    data.put("apple", String.valueOf(Integer.parseInt(data.get("apple")) + 1));
                    break;
                case 1:
                    data.put("orange", String.valueOf(Integer.parseInt(data.get("orange")) + 1));
                    break;
                case 2:
                    data.put("strawberry", String.valueOf(Integer.parseInt(data.get("strawberry")) + 1));
                    break;
                case 3:
                    data.put("banana", String.valueOf(Integer.parseInt(data.get("banana")) + 1));
                    break;
                case 4:
                    data.put("ybone", String.valueOf(Integer.parseInt(data.get("ybone")) + 1));
                    break;
                case 5:
                    data.put("bbone", String.valueOf(Integer.parseInt(data.get("bbone")) + 1));
                    break;

            }

            DataManager.saveState(saveFileName, data); // Save the updated state
        }
    }
}

