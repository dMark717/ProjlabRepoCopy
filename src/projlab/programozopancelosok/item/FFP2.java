package projlab.programozopancelosok.item;

import projlab.programozopancelosok.entity.Person;
import projlab.programozopancelosok.util.Logger;

/**
 * Represents an FFP2 item in the game.
 */
public class FFP2 extends Item {

    public FFP2(boolean validItem, boolean sticky) {
        super(validItem, sticky);
    }

    /**
     * Activates the FFP2 item, causing the person to put on a mask.
     *
     * @param p The person using the item.
     */
    @Override
    public void onUse(Person p) {
        Logger.CALLSTACK_PRINTLN("FFP2.onUse(Person p) called");
        if (isValid())
            p.putOnMask();
        expire();
    }

    @Override
    public void printInfo() {
        System.out.print("FFP2");
    }

    @Override
    public String toString() {
        return "FFP2";
    }
}
