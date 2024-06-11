package projlab.programozopancelosok.item;

import projlab.programozopancelosok.entity.Person;
import projlab.programozopancelosok.room.strategy.NormalStrategy;
import projlab.programozopancelosok.room.strategy.RoomStrategy;

/**
 * Represents an air freshener item.
 * An air freshener can be used to refresh the air in a room by accepting it and replacing the current room strategy with a normal one.
 */
public class AirFreshener extends Item {

    /**
     * Initializes an air freshener item.
     *
     * @param validItem Indicates if the item is valid.
     * @param sticky    Indicates if the item is sticky.
     */
    public AirFreshener(boolean validItem, boolean sticky) {
        super(validItem, sticky);
    }

    /**
     * Uses the air freshener item.
     *
     * @param p The person using the air freshener.
     */
    @Override
    public void onUse(Person p) {
        for (RoomStrategy str : p.getCurrentRoom().getStrategies()) {
            str.acceptAirFreshener();
        }
        this.expire();
        p.getCurrentRoom().addStrategy(new NormalStrategy());
    }

    /**
     * Prints information about the air freshener item.
     */
    @Override
    public void printInfo() {
        System.out.print("AirFrh");
    }

    @Override
    public String toString() {
        return "AirFreshener";
    }
}

