package projlab.programozopancelosok.entity;

import projlab.programozopancelosok.control.Control;
import projlab.programozopancelosok.item.Item;
import projlab.programozopancelosok.room.Door;
import projlab.programozopancelosok.room.Room;
import projlab.programozopancelosok.util.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Abstract class representing a person in the game.
 */
public abstract class Person implements java.io.Serializable {

    /**
     * The current room the person is in.
     */
    Room currentRoom;

    /**
     * The inventory of the person, with a maximum capacity of 5 items.
     */
    List<Item> inventory;
    int selectedItemIndex;
    int maxItemCount;
    boolean isAlive = true;
    /**
     * The stun time of the person.
     */
    double stunTime;

    /**
     * The invincibility time of the person.
     */
    double invincibilityTime;

    /**
     * The remaining time for mask usage.
     */
    double maskTime;

    /**
     * The maximum time allowed for mask usage.
     */
    static final double MASK_MAX = 30.0;

    /**
     * The Neptun code of the person.
     */
    String neptun;

    public String getNeptun() {
        return neptun;
    }

    /**
     * Constructs a Person object with an empty inventory.
     */
    public Person(String neptun) {
        this.inventory = new ArrayList<>();
        maxItemCount = 5;
        this.neptun = neptun;
    }

    /**
     * Chooses a door to enter the room and calls its enter() method.
     *
     * @param d The door to choose.
     */
    public abstract boolean chooseDoor(Door d);

    /**
     * Visits a room and notifies the room about the visitor.
     *
     * @param room The room to visit.
     */
    public abstract void visit(Room room);

    /**
     * Meets another person and determines their role (student or teacher).
     *
     * @param person The person to meet.
     */
    public abstract void meet(Person person);

    /**
     * Accepts a student for interaction.
     *
     * @param student The student to accept.
     */
    abstract void acceptStudent(Student student);

    /**
     * Accepts a teacher for interaction.
     *
     * @param teacher The teacher to accept.
     */
    abstract void acceptTeacher(Teacher teacher);

    /**
     * Accepts a cleaner for interaction.
     *
     * @param cleaner The cleaner to accept.
     */
    abstract void acceptCleaner(Cleaner cleaner);

    /**
     * Executes actions on every tick of the game.
     */
    public void onTick() {
        // Mask usage
        if (currentRoom.isPoisonous())
            maskTime = Math.max(maskTime - Control.TICK_LENGTH_s, 0.0);

        invincibilityTime = Math.max(invincibilityTime - Control.TICK_LENGTH_s, 0.0);

        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            if (item.isExpired()) {
                inventory.remove(i--);
                continue;
            }
            item.onTick();
        }
        stunTime = Math.max(stunTime - Control.TICK_LENGTH_s, 0.0);
    }

    /**
     * Stuns the person for a specified duration.
     *
     * @param stunTime The duration of stun.
     */
    public void stun(double stunTime) {
        Logger.CALLSTACK_PRINTLN("Person.stun(stunTime) called.");
        this.stunTime = stunTime;
    }

    /**
     * Causes the person to faint for a specified duration, dropping all items.
     *
     * @param stunTime The duration of faint.
     */
    public void faint(double stunTime) {
        Logger.CALLSTACK_PRINTLN("Person.faint(stunTime) called.");
        dropAllItems();
        stun(stunTime);
    }

    /**
     * Checks if the person can move.
     *
     * @return True if the person is not stunned and can move, otherwise false.
     */
    public boolean canMove() {
        return !isStunned();
    }


    /**
     * Drops the selected item from the student's inventory into the current room.
     */
    public void dropSelectedItem() {
        Logger.CALLSTACK_PRINTLN("Student.dropSelectedItem(item) called");
        Item item = inventory.get(selectedItemIndex);
        inventory.remove(selectedItemIndex);
        currentRoom.addItem(item);
    }

    /**
     * Drops all items carried by the person into the current room.
     */
    private void dropAllItems() {
        Logger.CALLSTACK_PRINTLN("Person.dropAllItems() called.");
        for (Item item : inventory) {
            currentRoom.addItem(item);
        }
        inventory.clear();
    }

    /**
     * Puts on a mask.
     */
    public void putOnMask() {
        Logger.CALLSTACK_PRINTLN("Person.putOnMask() called");
        maskTime = MASK_MAX;
    }


    /**
     * Checks if the mask is on.
     *
     * @return True if the mask is on, false otherwise.
     */
    public boolean isMaskOn() {
        Logger.CALLSTACK_PRINTLN("Person.isMaskOn() called");
        boolean ret = maskTime > 0.0;
        Logger.CALLSTACK_PRINTLN("\tPerson.isMaskOn() returned with " + ret + ".");
        return maskTime > 0.0;
    }

    /**
     * Checks if the person is alive.
     *
     * @return
     */

    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the invincibility time of the person.
     *
     * @param second The duration of invincibility.
     */
    public void setInvincibility(double second) {
        Logger.CALLSTACK_PRINTLN("Person.setInvincibility(double second) called");
        invincibilityTime = second;
    }

    /**
     * Checks if the person is invincible.
     *
     * @return True if the person is invincible, false otherwise.
     */
    public boolean isInvincible() {
        return invincibilityTime > 0.0;
    }

    /**
     * Retrieves the current room of the person.
     *
     * @return The current room.
     */
    public Room getCurrentRoom() {
        Logger.CALLSTACK_PRINTLN("Person.getCurrentRoom() called");
        Logger.CALLSTACK_PRINTLN("\tPerson.getCurrentRoom() returned with current room");
        return currentRoom;
    }

    /**
     * Sets the current room of the person.
     *
     * @param room The room to set as the current room.
     */
    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }


    /**
     * Attempts to pick up an item, adding it to the inventory if there is space.
     *
     * @param item The item to pick up.
     * @return True if the item was successfully picked up, false otherwise.
     */
    public boolean pickUpItem(Item item) {
        Logger.CALLSTACK_PRINTLN("Person.pickUpItem() called.");
        if (inventory.size() < maxItemCount && !item.isSticky()) {
            Logger.CALLSTACK_PRINTLN("\tPerson.pickUpItem() returned with true.");
            inventory.add(item);
            item.onPickup(this);
            return true;
        }
        Logger.CALLSTACK_PRINTLN("\tPerson.pickUpItem() returned with false.");
        return false;
    }

    /**
     * Drops a randomly selected item from the person's inventory into the current room.
     * If the inventory is empty, no action is taken.
     */
    public void dropRandomItem() {
        Random random = new Random();
        if (!inventory.isEmpty()) {
            int id = random.nextInt(0, inventory.size());
            Item item = inventory.get(id);
            inventory.remove(id);
            this.currentRoom.addItem(item);
        }
    }

    /**
     * Sets the index of the selected item in the inventory.
     *
     * @param index The index of the selected item.
     */
    public void setSelectedItemIndex(int index) {
        if (selectedItemIndex < inventory.size()) {
            selectedItemIndex = index;
        }
    }

    /**
     * Uses the selected item from the inventory.
     *
     * @return True if the item is successfully used, false otherwise.
     */
    public boolean useSelectedItem() {
        if (inventory.get(selectedItemIndex) != null) {
            Item item = inventory.get(selectedItemIndex);
            item.onUse(this);
            return true;
        }
        return false;
    }

    /**
     * Checks if the person is currently stunned.
     *
     * @return True if the person is stunned, false otherwise.
     */
    public boolean isStunned() {
        return stunTime > 0;
    }

// Test output related methods

    /**
     * Prints the information of all items in the inventory.
     */
    public void printInventory() {
        for (Item i : inventory) {
            i.printInfo();
            System.out.print(" ");
        }
    }

    /**
     * Removes an item from the inventory based on its ID.
     *
     * @param id The ID of the item to be removed.
     */
    public void removeItemFromInventory(int id) {
        inventory.remove(id);
    }

    /**
     * Retrieves the item at the specified index in the inventory.
     *
     * @param id The index of the item to retrieve.
     * @return The item at the specified index, or null if the index is out of bounds.
     */
    public Item getItemAt(int id) {
        if (id <= (inventory.size() - 1)) {
            return inventory.get(id);
        }
        return null;
    }

    /**
     * Retrieves the currently selected item from the inventory.
     *
     * @return The currently selected item.
     */
    public Item getSelectedItem() {
        return inventory.get(selectedItemIndex);
    }

    /**
     * Prints the remaining mask time of the person.
     */
    public void printMaskTime() {
        System.out.print(maskTime);
    }

    /**
     * Prints the remaining invincibility time of the person.
     */
    public void printInvincibilityTime() {
        System.out.print(invincibilityTime);
    }

    /**
     * Prints the remaining stun time of the person.
     */
    public void printStunTime() {
        System.out.print(stunTime);
    }

    /**
     * Abstract method to print information about the person.
     *
     * @param pInventory Flag to determine whether to print inventory information.
     */
    public abstract void printInfo(boolean pInventory);

    /**
     * Gets the count of items in the inventory.
     *
     * @return The count of items in the inventory.
     */
    public int getItemCount() {
        return inventory.size();
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public double getMaskTime() {
        return maskTime;
    }

    public double getStunTime() {
        return stunTime;
    }

    public double getInvincibilityTime() {
        return invincibilityTime;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }
}

