package UI;

import Pets.Dog;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame{
    public MainScreen() {
        super("Main Window"); // Set title using super constructor
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the window

        KeyboardListener keyboardListener = new KeyboardListener();
        this.addKeyListener(keyboardListener);
        this.setFocusable(true);
        this.requestFocus();

        JLabel label = new JLabel("LOGIN MENU", SwingConstants.CENTER);
        label.setFont(new Font("SANS", Font.BOLD, 24));
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);

        JButton parentalButton = new JButton("Parental Controls");
        JButton startGameButton = new JButton("Start Game");

        panel.add(startGameButton);
        panel.add(parentalButton);
        panel.add(label);
        panel.add(parentalButton);

        this.add(panel);
        Dog dog = new Dog("John", this);
        setVisible(true); // Make the JFrame visible
    }
}