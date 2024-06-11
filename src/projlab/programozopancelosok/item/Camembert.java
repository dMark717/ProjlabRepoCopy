package projlab.programozopancelosok.item;

import projlab.programozopancelosok.entity.Person;
import projlab.programozopancelosok.room.Room;
import projlab.programozopancelosok.room.strategy.CamembertStrategy;
import projlab.programozopancelosok.util.Logger;

/**
 * Represents a Camembert item in the game.
 */
public class Camembert extends Item {

    /**
     * The maximum duration for Camembert usage.
     */
    public static final double MAX_CAMB_TIME_s = 30.0;

    public Camembert(boolean validItem, boolean sticky) {
        super(validItem, sticky);
    }

    /**
     * Activates the Camembert item, adding a Camembert strategy to the current room.
     *
     * @param p The person using the item.
     */
    @Override
    public void onUse(Person p) {
        Logger.CALLSTACK_PRINTLN("Camembert.OnUse(student) called");
        Room room = p.getCurrentRoom();
        room.addStrategy(new CamembertStrategy(MAX_CAMB_TIME_s));
        expire();
    }

    @Override
    public void printInfo() {
        System.out.print("Cambrt");
    }

    @Override
    public String toString() {
        return "Camambert";
    }
}

