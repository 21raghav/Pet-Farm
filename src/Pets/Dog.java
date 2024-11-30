package Pets;

import Animation.DogAnimation;

public class Dog extends Pet {
    public Dog(String name) {
        super(name, new DogAnimation());

    }
}
