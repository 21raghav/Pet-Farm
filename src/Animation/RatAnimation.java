package Animation;

import javax.swing.*;
import java.io.IOException;

public class RatAnimation extends Animation{
    @Override
    public void loadAnimations() {
        try {
            // Load all animations
            this.idleFrames = loadFrames(
                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/6 Rat 2/Idle.png", 4, 32, 32);
//            attackFrames = loadFrames(
//                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/6 Rat 2/Attack.png", 4);
            deathFrames = loadFrames(
                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/6 Rat 2/Death.png", 2, 32, 32);
            hurtFrames = loadFrames(
                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/6 Rat 2/Hurt.png", 2, 32, 32);
            walkFrames = loadFrames(
                    "Assets/craftpix-net-610575-free-street-animal-pixel-art-asset-pack/6 Rat 2/Walk.png", 4, 32, 32);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading animation assets: " + e.getMessage(),
                    "Asset Loading Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
