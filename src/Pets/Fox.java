package Pets;

import Animation.DogAnimation;
import Animation.FoxAnimation;

public class Fox extends Pet{
    public Fox(String name) {
        super(name, new FoxAnimation());

    }
}
