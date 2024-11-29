package Pets;

import Animation.DogAnimation;
import Animation.AnimationState;

public class Dog extends Pet {
    public Dog(String name) {
        super(name, new DogAnimation());

    }
}
