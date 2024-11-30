package Animation;

import javax.swing.*;
import java.io.IOException;

public class DogAnimation extends Animation{
    @Override
    public void loadAnimations() {
        try {
            // Load all animations
            this.idleFrames = loadFrames(
                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/1 Dog/Idle.png", 4, 48, 48);
            attackFrames = loadFrames(
                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/1 Dog/Attack.png", 4, 48, 48);
            deathFrames = loadFrames(
                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/1 Dog/Death.png", 4, 48, 48);
            hurtFrames = loadFrames(
                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/1 Dog/Hurt.png", 2, 48, 48);
            walkFrames = loadFrames(
                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/1 Dog/Walk.png", 4, 48, 48);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading animation assets: " + e.getMessage(),
                    "Asset Loading Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
