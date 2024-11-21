package Pets;

import Animation.DogAnimation;

import javax.swing.*;

/**
 * The Dog class represents a specific type of pet, inheriting from the {@link Pet} class.
 * It includes functionality for initializing a dog with a name and adding its animation
 * to a given JFrame.
 */
public class Dog extends Pet {

    /**
     * Constructs a Dog instance with the specified name and initializes its animation
     * within the provided JFrame.
     *
     * @param name  The name of the dog.
     * @param frame The JFrame to which the dog's animation panel will be added.
     */
    public Dog(String name, JFrame frame) {
        super(name); // Call the parent class (Pet) constructor to initialize the name
        DogAnimation animationPanel = new DogAnimation(); // Create the dog's animation panel
        frame.add(animationPanel); // Add the animation panel to the JFrame
    }
}
