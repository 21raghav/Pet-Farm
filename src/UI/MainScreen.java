package UI;

import Animation.CatAnimation;
import Animation.DogAnimation;
import Animation.FoxAnimation;
import Animation.RatAnimation;
import Pets.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class MainScreen extends JFrame {
    private JLabel imageLabel;
    private Image originalImage;
    private JLabel animalLabel; // Label for the animal image

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
                    changeImage("Assets/GameImages/LoadGame.png", "Load Game Menu");
                }
                else if (isWithinBounds(x, y, (int) (700 * xScale), (int) (450 * yScale), (int) (400 * xScale), (int) (120 * yScale))) {
                    ButtonUtils.playSound(clickSoundPath);
                    new LoadGame();
                    dispose();
                }
                else if (isWithinBounds(x, y, (int) (250 * xScale), (int) (450 * yScale), (int) (400 * xScale), (int) (200 * yScale))) {
                    ButtonUtils.playSound(clickSoundPath);
                    changeImage("Assets/GameImages/PetSelection.png", "Pet Selection");
                }
                if (isWithinBounds(x, y, (int) (600 * xScale), (int) (200 * yScale), (int) (200 * xScale), (int) (200 * yScale))) {
                    // Top right animal in Pet Selection screen
                    ButtonUtils.playSound(clickSoundPath); // Play sound
                    Pet selectedPet = new Fox(new FoxAnimation()); // Replace 'Fox' with the appropriate class for the selected pet
                    SwingUtilities.invokeLater(() -> new GameMenu(selectedPet));
                    //dispose(); // Close the pet selection window
                }
                else if (isWithinBounds(x, y, (int) (1100 * xScale), (int) (200 * yScale), (int) (200 * xScale), (int) (200 * yScale))) {
                    // Top left animal in Pet Selection screen
                    ButtonUtils.playSound(clickSoundPath); // Play sound
                    Pet selectedPet = new Dog(new DogAnimation()); // Replace 'Fox' with the appropriate class for the selected pet
                    SwingUtilities.invokeLater(() -> new GameMenu(selectedPet));
                    //dispose(); // Close the pet selection window
                }

                else if (isWithinBounds(x, y, (int) (600 * xScale), (int) (600 * yScale), (int) (200 * xScale), (int) (200 * yScale))) {
                    // Bottom left animal in Pet Selection screen
                    ButtonUtils.playSound(clickSoundPath); // Play sound
                    Pet selectedPet = new Cat(new CatAnimation()); // Replace 'Fox' with the appropriate class for the selected pet
                    SwingUtilities.invokeLater(() -> new GameMenu(selectedPet));
                    //dispose(); // Close the pet selection window
                }

                else if (isWithinBounds(x, y, (int) (1100 * xScale), (int) (600 * yScale), (int) (200 * xScale), (int) (200 * yScale))) {
                    // Bottom right animal in Pet Selection screen
                    ButtonUtils.playSound(clickSoundPath); // Play sound
                    Pet selectedPet = new Rat(new RatAnimation()); // Replace 'Fox' with the appropriate class for the selected pet
                    SwingUtilities.invokeLater(() -> new GameMenu(selectedPet));
                    //dispose(); // Close the pet selection window
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
    private void changeImage(String imagePath, String newTitle) {
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

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainScreen frame = new MainScreen();
            frame.setVisible(true);
        });
    }
}
