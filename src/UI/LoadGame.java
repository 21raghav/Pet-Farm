package UI;

import Animation.CatAnimation;
import Animation.DogAnimation;
import Animation.FoxAnimation;
import Animation.RatAnimation;
import Game.DataManager;
import Pets.Cat;
import Pets.Dog;
import Pets.Fox;
import Pets.Pet;
import Pets.Rat;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

public class LoadGame extends JFrame {
    private static final int MAX_SAVES = 4;

    public LoadGame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create main panel with background image
        JPanel mainPanel = new JPanel() {
            @Override
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
        mainPanel.setLayout(null);

        // Get save file names
        String[] saveFiles = DataManager.getSaveFileNames();

        // Create save slot buttons
        for (int i = 0; i < MAX_SAVES; i++) {
            JButton saveSlotButton = createSaveSlotButton(i + 1, (i < saveFiles.length) ? saveFiles[i] : null);
            saveSlotButton.setBounds(110 + (i * 350), 390, 320, 240);
            mainPanel.add(saveSlotButton);
        }

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 50, 100, 40);
        backButton.addActionListener(e -> {
            new MainScreen();
            dispose();
        });
        mainPanel.add(backButton);

        add(mainPanel);
        setVisible(true);
    }

    private JButton createSaveSlotButton(int slotNumber, String saveFileName) {
        String buttonText = (saveFileName == null) ? "Slot " + slotNumber : saveFileName;
        JButton button = new JButton(buttonText);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        button.setFont(new Font("Arial", Font.BOLD, 16));

        if (saveFileName == null) {
            button.setEnabled(false); // Disable button if no save exists
        } else {
            button.addActionListener(e -> loadSaveFile(saveFileName));
        }

        return button;
    }

    private void loadSaveFile(String saveFileName) {
        Map<String, String> attributes = DataManager.loadState("", saveFileName);

        if (!attributes.isEmpty()) {
            Pet loadedPet;

            switch (saveFileName.toLowerCase()) {
                case "fox":
                    loadedPet = new Fox(new FoxAnimation());
                    break;
                case "dog":
                    loadedPet = new Dog(new DogAnimation());
                    break;
                case "cat":
                    loadedPet = new Cat(new CatAnimation());
                    break;
                case "rat":
                    loadedPet = new Rat(new RatAnimation());
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Unknown pet type.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            // Set attributes for the loaded pet
            loadedPet.setHealth(Integer.parseInt(attributes.get("health")));
            loadedPet.setHungerLevel(Integer.parseInt(attributes.get("hunger")));
            loadedPet.setSleepLevel(Integer.parseInt(attributes.get("sleep")));

            // Launch GameMenu with the loaded pet
            new GameMenu(loadedPet);
            dispose();
            // Launch GameMenu with the loaded pet
            //SwingUtilities.invokeLater(() -> new GameMenu(loadedPet).setVisible(true));
        } else {
            JOptionPane.showMessageDialog(this, "Failed to load save file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
