package Consumable;

import Pets.Pet;

public class FoodConsumable implements Consumable{
    private boolean isConsumed = false;
    private final int healthPoints;

    public FoodConsumable(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public boolean consume(Pet pet) {
        if (isConsumed) {
            return false;
        }
        isConsumed = true;
        pet.increaseHealth(healthPoints);
        return true;
    }
}