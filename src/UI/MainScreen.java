package UI;

import Animation.CatAnimation;
import Animation.DogAnimation;
import Animation.FoxAnimation;
import Animation.RatAnimation;
import Game.DataManager;
import Pets.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.time.LocalTime;

public class MainScreen extends JFrame {
    private JLabel imageLabel;
    private Image originalImage;
    private JLabel animalLabel; // Label for the animal image
    private boolean isLoadGame = false;
    private JFrame frame;

    public MainScreen() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize window
        //setResizable(true); // Allow resizing which enables the maximize button
        setUndecorated(false);  // Remove window borders and bars

        // Load the initial image
        ImageIcon mainMenuImage = new ImageIcon("Assets/GameImages/MainMenu.png");
        originalImage = mainMenuImage.getImage();
        imageLabel = new JLabel(new ImageIcon(originalImage));

        MusicUtils.playBackgroundMusic("Assets/Sounds/MenuMusic.wav");

        // Set up the frame
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080); // Default resolution set to 1920x1080
        setLayout(null);

        // Add the text in the bottom-right corner
        JLabel bottomRightLabel = new JLabel("<html>Developers: Arya Zarei, Raghav Gulati, Karl Seryani, Mark Samwaiel, Tarik Samer Alansari<br>" +
                "Team Number: 24<br>" +
                "Term: Fall 2024<br>" +
                "Created as part of CS2212 at Western University</html>");
        bottomRightLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bottomRightLabel.setForeground(Color.BLACK);
        bottomRightLabel.setBounds(getWidth() - 420, getHeight() - 120, 400, 100);
        add(bottomRightLabel);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelWidth = 400;
                int panelHeight = 200;
                bottomRightLabel.setBounds(getWidth() - panelWidth - 20, getHeight() - panelHeight - 20, panelWidth, panelHeight);
                bottomRightLabel.repaint();
            }
        });

        // Ensure the text label is on top of the background
        bottomRightLabel.setVisible(true);
        imageLabel.setVisible(true);
        setVisible(true);


        // Set the bounds of the image label and add it to the frame
        imageLabel.setBounds(0, 0, 1920, 1080); // Cover the entire screen
        add(imageLabel);

        // Add a listener to handle window resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int newWidth = getWidth();
                int newHeight = getHeight();

                // Scale the image to cover the entire screen
                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));

                // Update the bounds of the imageLabel to match the new frame size
                imageLabel.setBounds(0, 0, newWidth, newHeight);

                // Re-center the animal label if it exists
                if (animalLabel != null) {
                    int animalX = newWidth / 2 - animalLabel.getWidth() / 2;
                    int animalY = newHeight / 2 - animalLabel.getHeight() / 2;
                    animalLabel.setBounds(animalX, animalY, animalLabel.getWidth(), animalLabel.getHeight());
                }

                // Refresh the frame
                revalidate();
                repaint();
            }
        });

        // Use a mouse listener to detect clicks
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                // Calculate scale factors based on the current size of the imageLabel
                double xScale = (double) imageLabel.getWidth() / 1920; // Adjust based on new resolution
                double yScale = (double) imageLabel.getHeight() / 1080;

                // Add sound effect path
                String clickSoundPath = "Assets/Sounds/click.wav";


                // Detect button clicks and play sound
                if (isWithinBounds(x, y, (int) (835 * xScale), (int) (620 * yScale), (int) (400 * xScale), (int) (120 * yScale))) {
                    ButtonUtils.playSound(clickSoundPath); // Play sound on "Game" button click
                    Map<String,String> restrictions = DataManager.loadState("", "restrictions.csv");
                    if (LocalTime.now().isAfter(LocalTime.parse(restrictions.get("StartTime"))) && LocalTime.now().isBefore(LocalTime.parse(restrictions.get("EndTime")))) {
                        changeImage("Assets/GameImages/LoadGame.png", "Load Game Menu");
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "Parental restrictions do not allow gameplay at this moment.");
                    }
                }
                else if (isWithinBounds(x, y, (int) (700 * xScale), (int) (450 * yScale), (int) (400 * xScale), (int) (120 * yScale))) {
                    ButtonUtils.playSound(clickSoundPath);
                    changeImage("Assets/GameImages/PetSelection.png", "Pet Selection");
                    isLoadGame = true;
                }
                else if (isWithinBounds(x, y, (int) (250 * xScale), (int) (450 * yScale), (int) (400 * xScale), (int) (200 * yScale))) {
                    ButtonUtils.playSound(clickSoundPath);
                    changeImage("Assets/GameImages/PetSelection.png", "Pet Selection");

                }
                if (isWithinBounds(x, y, (int) (600 * xScale), (int) (200 * yScale), (int) (200 * xScale), (int) (200 * yScale))) {
                    // Top left animal in Pet Selection screen
                    if (!isLoadGame) {
                        DataManager.resetState("slot3.csv");
                    }
                    ButtonUtils.playSound(clickSoundPath); // Play sound
                    Pet selectedPet = new Fox(new FoxAnimation());
                    SwingUtilities.invokeLater(() -> new GameMenu(selectedPet));
                    dispose(); // Close the pet selection window
                    new MainScreen();

                }
                else if (isWithinBounds(x, y, (int) (1100 * xScale), (int) (200 * yScale), (int) (200 * xScale), (int) (200 * yScale))) {
                    // Top right animal in Pet Selection screen
                    if (!isLoadGame) {
                        DataManager.resetState("slot1.csv");
                    }
                    ButtonUtils.playSound(clickSoundPath); // Play sound
                    Pet selectedPet = new Dog(new DogAnimation());
                    SwingUtilities.invokeLater(() -> new GameMenu(selectedPet));
                    dispose(); // Close the pet selection window
                    new MainScreen();

                }

                else if (isWithinBounds(x, y, (int) (600 * xScale), (int) (600 * yScale), (int) (200 * xScale), (int) (200 * yScale))) {
                    // Bottom left animal in Pet Selection screen
                    if (!isLoadGame) {
                        DataManager.resetState("slot2.csv");
                    }
                    ButtonUtils.playSound(clickSoundPath); // Play sound
                    Pet selectedPet = new Cat(new CatAnimation());
                    SwingUtilities.invokeLater(() -> new GameMenu(selectedPet));
                    dispose(); // Close the pet selection window
                    new MainScreen();
                }

                else if (isWithinBounds(x, y, (int) (1100 * xScale), (int) (600 * yScale), (int) (200 * xScale), (int) (200 * yScale))) {
                    // Bottom right animal in Pet Selection screen
                    if (!isLoadGame) {
                        DataManager.resetState("slot4.csv");
                    }
                    ButtonUtils.playSound(clickSoundPath); // Play sound
                    Pet selectedPet = new Rat(new RatAnimation());
                    SwingUtilities.invokeLater(() -> new GameMenu(selectedPet));
                    dispose(); // Close the pet selection window
                    new MainScreen();

                }
                else if (isWithinBounds(x, y, (int) (1400 * xScale), (int) (450 * yScale), (int) (400 * xScale), (int) (200 * yScale))) {
                    ButtonUtils.playSound(clickSoundPath); // Play sound on "Go Back" button click in Load Game Menu
                    changeImage("Assets/GameImages/MainMenu.png", "Main Menu");
                }else if (isWithinBounds(x, y, (int) (835 * xScale), (int) (760 * yScale), (int) (400 * xScale), (int) (120 * yScale))) {
                    ButtonUtils.playSound(clickSoundPath);
                    SwingUtilities.invokeLater(() -> {
                        Pet tutorialPet = new Dog(new DogAnimation());
                        new TutorialGame(tutorialPet).setVisible(true);
                    });
                }
                else if (isWithinBounds(x, y, (int) (835 * xScale), (int) (920 * yScale), (int) (400 * xScale), (int) (120 * yScale))) {
                    ButtonUtils.playSound(clickSoundPath); // Play sound on "Parental" button click
                    new ParentalControlsScreen(MainScreen.this);
                }
            }
        });

        // Make the frame visible
        setVisible(true);
    }

    // Method to change the image and title
    void changeImage(String imagePath, String newTitle) {
        ImageIcon newImageIcon = new ImageIcon(imagePath);
        originalImage = newImageIcon.getImage();

        int newWidth = getWidth();
        int newHeight = getHeight();

        // Scale the image to cover the entire screen
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));

        // Update the bounds of the imageLabel to match the new frame size
        imageLabel.setBounds(0, 0, newWidth, newHeight);

        setTitle(newTitle);
        revalidate();
        repaint();
    }

    private boolean isWithinBounds(int x, int y, int rectX, int rectY, int rectWidth, int rectHeight) {
        return x >= rectX && x <= rectX + rectWidth && y >= rectY && y <= rectY + rectHeight;
    }
}
