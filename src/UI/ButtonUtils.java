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

    // Play a WAV sound file with dynamic format handling
    public static void playSound(String soundFilePath) {
        new Thread(() -> {
            try {
                File soundFile = new File(soundFilePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

                // Check and convert the audio format if necessary
                AudioFormat baseFormat = audioStream.getFormat();
                AudioFormat decodedFormat = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        baseFormat.getSampleRate(),
                        16, // 16-bit audio
                        baseFormat.getChannels(),
                        baseFormat.getChannels() * 2, // Frame size
                        baseFormat.getSampleRate(),
                        false // Little-endian
                );

                // Convert the audio stream to a supported format
                AudioInputStream decodedStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);

                // Play the audio
                Clip clip = AudioSystem.getClip();
                clip.open(decodedStream);
                clip.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}
