package Animation;

import javax.swing.*;
import java.io.IOException;

public class CatAnimation extends Animation{
    @Override
    public void loadAnimations() {
        try {
            // Load all animations
            this.idleFrames = loadFrames(
                    "Assets/Animals/3 Cat/Idle.png", 4, 48, 48);
            attackFrames = loadFrames(
                    "Assets/Animals/3 Cat/Attack.png", 4, 48, 48);
            deathFrames = loadFrames(
                    "Assets/Animals/3 Cat/Death.png", 4, 48, 48);
            hurtFrames = loadFrames(
                    "Assets/Animals/3 Cat/Hurt.png", 2, 48, 48);
            walkFrames = loadFrames(
                    "Assets/Animals/3 Cat/Walk.png", 4, 48, 48);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading animation assets: " + e.getMessage(),
                    "Asset Loading Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
