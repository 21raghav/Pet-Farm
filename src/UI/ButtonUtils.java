package UI;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;

public class ButtonUtils {

    // Add a sound effect to a JButton
    public static void addButtonClickSound(JButton button, String soundFilePath) {
        button.addActionListener(e -> playSound(soundFilePath));
    }

    // Add a sound effect to a JPasswordField when pressing "Enter"
    public static void addPasswordFieldEnterSound(JPasswordField passwordField, String soundFilePath, Runnable action) {
        passwordField.addActionListener(e -> {
            playSound(soundFilePath);
            action.run();
        });
    }

    // Play a WAV sound file
    public static void playSound(String soundFilePath) {
        new Thread(() -> {
            try {
                File soundFile = new File(soundFilePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}
