package Pets;

import Animation.CatAnimation;

public class Cat extends Pet{
    public Cat(String name) {
        super(name, new CatAnimation());

    }
}
