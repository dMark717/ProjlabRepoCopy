package projlab.programozopancelosok.item;

import projlab.programozopancelosok.entity.Person;
import projlab.programozopancelosok.util.Logger;

/**
 * Represents a HolyBeerCup item in the game.
 */
public class HolyBeerCup extends Item {

    /**
     * The maximum duration for HolyBeerCup usage.
     */
    public static final double MAX_BEER_TIME_s = 60.0;

    public HolyBeerCup(boolean validItem, boolean sticky) {
        super(validItem, sticky);
    }

    /**
     * Activates the HolyBeerCup item, granting invincibility to the person for a duration.
     *
     * @param p The person using the item.
     */
    @Override
    public void onUse(Person p) {
        Logger.CALLSTACK_PRINTLN("HolyBeerCup.onUse(Person p) called");
        p.setInvincibility(MAX_BEER_TIME_s);
        p.dropRandomItem();
        expire();
    }

    @Override
    public void printInfo() {
        System.out.print("Beer");
    }

    @Override
    public String toString() {
        return "Holy Beer";
    }
}

