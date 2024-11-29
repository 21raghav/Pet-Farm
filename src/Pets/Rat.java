package Pets;

import Animation.DogAnimation;
import Animation.RatAnimation;

public class Rat extends Pet{
    public Rat(String name) {
        super(name, new RatAnimation());

    }
}
