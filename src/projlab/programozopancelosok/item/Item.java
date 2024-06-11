package projlab.programozopancelosok.item;

import projlab.programozopancelosok.entity.Person;
import projlab.programozopancelosok.util.Logger;

/**
 * Represents an item in the game.
 */
public abstract class Item implements java.io.Serializable {
    /**
     * Indicates if the item is valid.
     */
    protected boolean validItem;

    /**
     * Indicates if the item is sticky.
     */
    protected boolean sticky;

    /**
     * Initializes an item.
     *
     * @param validItem Indicates if the item is valid.
     * @param sticky    Indicates if the item is sticky.
     */
    public Item(boolean validItem, boolean sticky) {
        this.validItem = validItem;
        this.sticky = sticky;
    }

    /**
     * Checks if the item is sticky.
     *
     * @return true if the item is sticky, otherwise false.
     */
    public boolean isSticky() {
        return sticky;
    }

    /**
     * Sets the stickiness of the item.
     *
     * @param b The value to set for stickiness.
     */
    public void setSticky(boolean b) {
        sticky = b;
    }

    /**
     * Checks if the item is valid.
     *
     * @return true if the item is valid, otherwise false.
     */
    public boolean isValid() {
        return validItem;
    }


    /**
     * Indicates if the item is expired.
     */
    private boolean expired = false;

    /**
     * Marks the item as expired, indicating to the person that it should be removed.
     */
    protected void expire() {
        Logger.CALLSTACK_PRINTLN("Item.expire() called");
        expired = true;
    }

    /**
     * Executes logic when the game progresses by one tick.
     */
    public void onTick() {
    }

    /**
     * Executes logic when the item is picked up by a person.
     */
    public void onPickup(Person p) {
    }

    /**
     * Executes logic when the item is used by a person.
     *
     * @param p The person using the item.
     */
    public void onUse(Person p) {
    }

    /**
     * Checks if the item blocks death.
     *
     * @return True if the item blocks death, false otherwise.
     */
    public boolean blockDeath() {
        System.out.println("Item.blockDeath() returned with false.");
        return false;
    }

    /**
     * Checks if the item is expired.
     *
     * @return True if the item is expired, false otherwise.
     */
    public final boolean isExpired() {
        return expired;
    }

    public abstract void printInfo();

    public boolean acceptTransistor(Transistor t) {
        return false;
    }
}

