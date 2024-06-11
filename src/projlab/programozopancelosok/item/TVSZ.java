package projlab.programozopancelosok.item;

import projlab.programozopancelosok.util.Logger;

/**
 * Represents a TVSZ (Tanulmányi és Vizsgaszabályzat) item in the game.
 */
public class TVSZ extends Item {
    /**
     * The number of charges left for the TVSZ item.
     */
    int chargesLeft = 3;

    public TVSZ(boolean validItem, boolean sticky) {
        super(validItem, sticky);
    }

    /**
     * Blocks death for the person using the TVSZ item, reducing charges.
     *
     * @return True if death is blocked, false otherwise.
     */

    @Override
    public boolean blockDeath() {
        if (!isValid()) {
            expire();
            return false;
        }

        if (chargesLeft > 0) {
            chargesLeft--;
            if (chargesLeft == 0) expire();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void printInfo() {
        System.out.print("TVSZ");
    }

    @Override
    public String toString() {
        return "TVSZ(" + chargesLeft + ")";
    }
}
