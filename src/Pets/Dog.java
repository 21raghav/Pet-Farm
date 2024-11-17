package Pets;

import Animation.DogAnimation;

import javax.swing.*;

public class Dog extends Pet{
    public Dog(String name, JFrame frame) {
        super(name);
        DogAnimation animationPanel = new DogAnimation();
        frame.add(animationPanel);
    }


}
