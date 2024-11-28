package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainScreen extends JFrame {
    private JLabel imageLabel;
    private Image originalImage;
    private int originalWidth = 800;
    private int originalHeight = 600;

    public MainScreen() {
        // Load the initial image
        ImageIcon mainMenuImage = new ImageIcon("Assets/GameImages/MainMenu.png");
        originalImage = mainMenuImage.getImage();
        imageLabel = new JLabel(new ImageIcon(originalImage));

        MusicUtils.playBackgroundMusic("Assets/Sounds/MenuMusic.wav");

        // Set up the frame
        setTitle("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(originalWidth, originalHeight);
        setLayout(null);

        // Set the bounds of the image label and add it to the frame
        imageLabel.setBounds(0, 0, originalWidth, originalHeight);
        add(imageLabel);

        // Add a listener to handle window resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int newWidth = getWidth();
                int newHeight = getHeight();

                // Calculate aspect ratio
                double aspectRatio = (double) originalWidth / originalHeight;

                // Determine new dimensions that fit within the window
                int scaledWidth = newWidth;
                int scaledHeight = (int) (newWidth / aspectRatio);
                if (scaledHeight > newHeight) {
                    scaledHeight = newHeight;
                    scaledWidth = (int) (newHeight * aspectRatio);
                }

                // Scale the image to fit within the window
                Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));

                // Center the image within the frame
                int x = (newWidth - scaledWidth) / 2;
                int y = (newHeight - scaledHeight) / 2;
                imageLabel.setBounds(x, y, scaledWidth, scaledHeight);

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

                // Calculate scale factors
                double xScale = (double) imageLabel.getWidth() / originalWidth;
                double yScale = (double) imageLabel.getHeight() / originalHeight;

                // Add sound effect path
                String clickSoundPath = "Assets/Sounds/click.wav";

                // Detect button clicks and play sound
                if (isWithinBounds(x, y, (int) (357 * xScale), (int) (340 * yScale), (int) (110 * xScale), (int) (70 * yScale))) {
                    ButtonUtils.playSound(clickSoundPath); // Play sound on "Game" button click
                    changeImage("Assets/GameImages/LoadGame.png", "Load Game Menu");
                } else if (isWithinBounds(x, y, (int) (350 * xScale), (int) (480 * yScale), (int) (110 * xScale), (int) (70 * yScale))) {
                    ButtonUtils.playSound(clickSoundPath); // Play sound on "Parental Controls" button click
                    new ParentalControlsScreen(MainScreen.this);
                } else if (isWithinBounds(x, y, (int) (600 * xScale), (int) (250 * yScale), (int) (200 * xScale), (int) (100 * yScale))) {
                    ButtonUtils.playSound(clickSoundPath); // Play sound on "Go Back" button click
                    changeImage("Assets/GameImages/MainMenu.png", "Main Menu");
                } else if (isWithinBounds(x, y, (int) (40 * xScale), (int) (250 * yScale), (int) (200 * xScale), (int) (100 * yScale))) {
                    ButtonUtils.playSound(clickSoundPath); // Play sound on "New Game" button click
                    changeImage("Assets/GameImages/PetSelection.png", "Pet Selection");
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
        double aspectRatio = (double) originalWidth / originalHeight;

        int scaledWidth = newWidth;
        int scaledHeight = (int) (newWidth / aspectRatio);
        if (scaledHeight > newHeight) {
            scaledHeight = newHeight;
            scaledWidth = (int) (newHeight * aspectRatio);
        }

        Image scaledImage = originalImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        int x = (newWidth - scaledWidth) / 2;
        int y = (newHeight - scaledHeight) / 2;
        imageLabel.setBounds(x, y, scaledWidth, scaledHeight);

        setTitle(newTitle);

        revalidate();
        repaint();
    }

    private boolean isWithinBounds(int x, int y, int rectX, int rectY, int rectWidth, int rectHeight) {
        return x >= rectX && x <= rectX + rectWidth && y >= rectY && y <= rectY + rectHeight;
    }

    public static void main(String[] args) {
        new MainScreen();
    }
}
