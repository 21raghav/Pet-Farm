package Consumable;

import Pets.Pet;

public class SleepConsumable implements Consumable{
    private boolean isConsumed = false;
    private final int sleepPoints;

    public SleepConsumable(int sleepPoints) {
        this.sleepPoints = sleepPoints;
    }

    public boolean consume(Pet pet) {
        if (isConsumed) {
            return false;
        }
        isConsumed = true;
        pet.increaseHealth(sleepPoints);
        return true;
    }
}