package projlab.programozopancelosok.entity.personcontroller;

import projlab.programozopancelosok.control.GameData;
import projlab.programozopancelosok.entity.Person;
import projlab.programozopancelosok.item.Item;
import projlab.programozopancelosok.item.Transistor;
import projlab.programozopancelosok.room.Door;
import projlab.programozopancelosok.room.Room;

/**
 * PersonController is an abstract class representing a controller for a person in the game.
 * It provides methods to control the actions of the person such as moving, picking up items,
 * selecting items, using selected items, dropping selected items, connecting transistors,
 * placing transistors, and handling ticks.
 */
public abstract class PersonController {
    /**
     * The person controlled by the controller.
     */
    protected final Person person;

    /**
     * The identifier associated with the controller.
     */
    protected final String id;

    /**
     * The game data associated with the controller.
     */
    protected final GameData gameData;

    /**
     * Constructs a PersonController with the specified person and game data.
     *
     * @param person   The person to be controlled.
     * @param gameData The game data.
     */
    public PersonController(Person person, GameData gameData) {
        this.person = person;
        this.id = person.getNeptun();
        this.gameData = gameData;
    }

    /**
     * Moves the person to the door at the specified index.
     *
     * @param index The index of the door to move to.
     * @return true if the person successfully moves, false otherwise.
     */
    public boolean moveTo(int index) {
        if (!person.canMove())
            return false;
        Room currentRoom = person.getCurrentRoom();
        Door door = currentRoom.getDoor(index);
        return door.enter(person);
    }

    /**
     * Allows the person to pick up the item at the specified index in the current room.
     *
     * @param index The index of the item to pick up.
     * @return true if the person successfully picks up the item, false otherwise.
     */
    public boolean pickupItem(int index) {
        Room room = person.getCurrentRoom();
        Item item = room.getItemAt(index);
        boolean isSuccess = person.pickUpItem(item);
        if (isSuccess)
            room.removeItem(item);
        return isSuccess;
    }

    /**
     * Selects the item at the specified index in the person's inventory.
     *
     * @param index The index of the item to select.
     * @return true if the item is successfully selected, false otherwise.
     */
    public boolean selectItem(int index) {
        person.setSelectedItemIndex(index);
        return true;
    }

    /**
     * Uses the selected item.
     *
     * @return true if the selected item is successfully used, false otherwise.
     */
    public boolean useSelectedItem() {
        person.useSelectedItem();
        return true;
    }

    /**
     * Drops the selected item.
     *
     * @return true if the selected item is successfully dropped, false otherwise.
     */
    public boolean dropSelectedItem() {
        person.dropSelectedItem();
        return false;
    }

    /**
     * Connects two transistors in the person's inventory.
     *
     * @param id0 The index of the first transistor.
     * @param id1 The index of the second transistor.
     * @return true if the transistors are successfully connected, false otherwise.
     */
    public boolean connectTrans(int id0, int id1) {
        Transistor t1;
        Transistor t2;
        try {
            t1 = (Transistor) person.getItemAt(id0);
            t2 = (Transistor) person.getItemAt(id1);
        } catch (Exception e) {
            return false;
        }

        t1.connect(t2);
        return true;
    }

    /**
     * Places a transistor in the specified room.
     *
     * @param id The index of the transistor to place.
     * @param in The identifier of the room to place the transistor in.
     * @return true if the transistor is successfully placed, false otherwise.
     */
    public boolean placeTrans(int id, String in) {
        Transistor t;
        try {
            t = (Transistor) person.getItemAt(id);
        } catch (Exception e) {
            return false;
        }
        Room r = gameData.getRoomManager().getRoom(in);

        person.removeItemFromInventory(id);
        return t.place(r);
    }

    /**
     * Handles the tick event by calling the corresponding method of the person.
     */
    public void onTick() {
        person.onTick();
    }

    /**
     * Gets the person associated with the controller.
     *
     * @return The person.
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Gets the identifier associated with the controller.
     *
     * @return The identifier.
     */
    public String getId() {
        return id;
    }
}
