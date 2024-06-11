package projlab.programozopancelosok.item;

import projlab.programozopancelosok.entity.Person;
import projlab.programozopancelosok.entity.Student;
import projlab.programozopancelosok.room.Room;
import projlab.programozopancelosok.util.Logger;

import java.util.Random;

/**
 * Represents a Transistor item in the game.
 */
public class Transistor extends Item {
    private Transistor connectedWith;
    private Room placedHere;
    private int connectId;


    public Transistor(boolean validItem, boolean sticky) {
        super(validItem, sticky);
        connectedWith = null;
        Random rand = new Random();

    }

    /**
     * Connects this transistor with another transistor.
     *
     * @param t The other transistor to createDoorBetween with.
     */
    public void connect(Transistor t) {
        Logger.CALLSTACK_PRINTLN("Transistor.createDoorBetween(Transistor t) called");
        Random rand = new Random();
        this.connectId = rand.nextInt(1, 1000);
        t.connectId = this.connectId;
        connectedWith = t;
        t.setConnectedWith(this);
    }

    /**
     * Sets the other transistor connected with this transistor.
     *
     * @param t The other transistor to set as connected.
     */
    public void setConnectedWith(Transistor t) {
        Logger.CALLSTACK_PRINTLN("Transistor.setConnectedWith(Transistor t) called");
        connectedWith = t;
    }

    /**
     * Places the transistor in a room.
     *
     * @param r The room to place the transistor in.
     */
    public boolean place(Room r) {
        if (this.connectedWith == null) return false;
        Logger.CALLSTACK_PRINTLN("Transistor.place(Room r) called");
        this.placedHere = r;

        r.placeTransistorInRoom(this);

        return true;
    }

    /**
     * Activates the transistor item, attempting to teleport the person using it.
     *
     * @param p The person using the item.
     */
    @Override
    public void onUse(Person p) {
        Logger.CALLSTACK_PRINTLN("Transistor.onUse(Person p) called");
        if (this.connectedWith == null) {

            boolean foundone = false;
            int i = 0;
            while (!foundone && i < p.getInventory().size()) {
                foundone = p.getInventory().get(i).acceptTransistor(this);
                i++;
            }
        } else if (this.connectedWith.placedHere == null) {
            this.place(p.getCurrentRoom());
            p.removeItemFromInventory(p.getSelectedItemIndex());
        } else {
            if (connectedWith.teleport(p)) {
                Logger.CALLSTACK_PRINTLN("Student teleported successfully");
            } else {
                Logger.CALLSTACK_PRINTLN("Student couldn't teleport successfully");
            }
        }


    }

    /**
     * Teleports the person to the room connected with this transistor if the room is not full.
     *
     * @param p The person to teleport.
     * @return True if the teleportation is successful, false otherwise.
     */
    public boolean teleport(Person p) {

        Logger.CALLSTACK_PRINTLN("Transistor.teleport(Person p) called");
        if (placedHere.isFull() || placedHere.equals(p.getCurrentRoom())) {
            return false;
        } else {
            Room temp = p.getCurrentRoom();
            placedHere.acceptStudent((Student) p);
            p.getCurrentRoom().removePerson(p);
            p.setCurrentRoom(placedHere);
            placedHere.getTransistorsInRoom().remove(this);
            this.place(temp);
            return true;
        }
    }

    @Override
    public boolean acceptTransistor(Transistor t) {
        if (this.connectedWith == null && !this.equals(t)) {
            this.connect(t);
            return true;
        }
        return false;
    }

    @Override
    public void printInfo() {
        System.out.print("Transistor");
    }

    @Override
    public String toString() {

        return "Transistor " + (connectId == 0 ? "" : connectId);
    }
}
