package UI;

import javax.sound.sampled.*;
import java.io.File;

public class MusicUtils {
    private static Clip backgroundClip;

    // Start looping background music
    public static void playBackgroundMusic(String musicFilePath) {
        try {
            if (backgroundClip == null || !backgroundClip.isRunning()) {
                File musicFile = new File(musicFilePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(audioStream);
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop indefinitely
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Stop background music
    public static void stopBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
            backgroundClip.close();
            backgroundClip = null;
        }
    }
}
