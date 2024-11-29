package Animation;

import javax.swing.*;

public interface Animation{
    void switchToIdle();
    void switchToWalking();
    void switchToSleeping();
    void switchToHurt();
    void setAnimation(AnimationState state);

    JPanel getPanel();

    void setLocation(int x, int y);

}
