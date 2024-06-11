package projlab.programozopancelosok.room;

import projlab.programozopancelosok.control.Control;
import projlab.programozopancelosok.entity.Person;
import projlab.programozopancelosok.util.Logger;

import java.util.Timer;

/**
 * Represents a door in the game.
 * Doors createDoorBetween two rooms and can be one-way or two-way.
 */

/**
 * Represents a door connecting two rooms.
 */
public class Door {

    /**
     * The room where the door originates.
     */
    private Room from;

    /**
     * The room where the door leads to.
     */
    private Room to;

    /**
     * Indicates if the door allows two-way traversal.
     */
    private boolean twoWay;

    /**
     * Indicates if the door is open.
     */
    private boolean isOpen;

    private double disappearTimer;

    /**
     * Initializes a door.
     *
     * @param from     The room where the door originates.
     * @param to       The room where the door leads to.
     * @param isTwoWay Indicates if the door allows two-way traversal.
     * @param isOpen   Indicates if the door is open.
     */
    public Door(Room from, Room to, boolean isTwoWay, boolean isOpen) {
        this.from = from;
        this.to = to;
        this.twoWay = isTwoWay;
        this.isOpen = isOpen;
    }

    /**
     * Initializes a door as a copy of another door.
     *
     * @param door The door to copy.
     */
    public Door(Door door) {
        this.from = door.from;
        this.to = door.to;
        this.twoWay = door.twoWay;
        this.isOpen = door.isOpen;
    }

    /**
     * Attempts to enter through the door.
     *
     * @param person The person attempting to enter.
     * @return True if the person successfully enters through the door, false otherwise.
     */
    public boolean enter(Person person) {
        Logger.CALLSTACK_PRINTLN("Door.enter(person) called.");
        Room origin = person.getCurrentRoom();
        if (canEnterFrom(origin)) {
            Room dest = getOpposite(origin);
            person.visit(dest);

            Logger.CALLSTACK_PRINTLN("\tDoor.enter(person) returned true.");
            return true;
        }
        Logger.CALLSTACK_PRINTLN("\tDoor.enter(person) returned false.");
        return false;
    }

    /**
     * Checks if the door connects two specific rooms.
     *
     * @param r1 One of the rooms to check.
     * @param r2 The other room to check.
     * @return True if the door connects the two specified rooms, false otherwise.
     */
    public boolean doesConnectThem(Room r1, Room r2) {
        return (from == r1 && to == r2) || (from == r2 && to == r1);
    }

    /**
     * Replaces one of the rooms connected to the door with another room.
     *
     * @param oldRoom The room to be replaced.
     * @param newRoom The room to replace the old room with.
     */
    public void replace(Room oldRoom, Room newRoom) {
        Logger.CALLSTACK_PRINTLN("Door.replace(oldRoom, newRoom) called");
        if (to.equals(oldRoom)) {
            to = newRoom;
        } else if (from.equals(oldRoom)) {
            from = newRoom;
        }

        Logger.CALLSTACK_PRINTLN("Replaceable room not found ! :(");
    }

    /**
     * Retrieves the room where the door originates.
     *
     * @return The room where the door originates.
     */
    public Room getFrom() {
        return from;
    }

    /**
     * Retrieves the room where the door leads to.
     *
     * @return The room where the door leads to.
     */
    public Room getTo() {
        return to;
    }

    /**
     * Checks if the door connects to a specified room.
     *
     * @param room The room to check against.
     * @return True if the door connects to the specified room, otherwise false.
     */
    public boolean isOnOneSide(Room room) {
        return room.equals(from) || room.equals(to);
    }

    /**
     * Retrieves the room on the opposite side of the specified room.
     *
     * @param origin The room from which to find the opposite room.
     * @return The room on the opposite side of the specified room.
     */
    public Room getOpposite(Room origin) {
        if (to.equals(origin))
            return from;
        else if (from.equals(origin))
            return to;
        return null;
    }

    /**
     * Checks if the door can be entered from a specified room.
     *
     * @param room The room from which to check if the door can be entered.
     * @return True if the door can be entered from the specified room, otherwise false.
     */
    public boolean canEnterFrom(Room room) {
        return (isOpen && (room.equals(from) || (twoWay && room.equals(to))));
    }

    /**
     * Checks if the door allows two-way traversal.
     *
     * @return True if the door allows two-way traversal, otherwise false.
     */
    public boolean isTwoWay() {
        return twoWay;
    }

    /**
     * Sets whether the door allows two-way traversal.
     *
     * @param twoWay True to allow two-way traversal, false otherwise.
     */
    public void setTwoWay(boolean twoWay) {
        this.twoWay = twoWay;
    }

    /**
     * Retrieves whether the door allows two-way traversal.
     *
     * @return True if the door allows two-way traversal, otherwise false.
     */
    public boolean getTwoWay() {
        return twoWay;
    }

    /**
     * Checks if the door is open.
     *
     * @return True if the door is open, otherwise false.
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Disappears the door.
     */
    public void disappear() {
        Logger.CALLSTACK_PRINTLN("Door.disappear() called");
        isOpen = false;
        if (disappearTimer == 0)
            disappearTimer = 30;
    }

    /**
     * Reappears the door.
     */
    public void reappear() {
        Logger.CALLSTACK_PRINTLN("Door.reappear() called");
        isOpen = true;
    }

    public void onTick() {
        disappearTimer = Math.max(disappearTimer - Control.TICK_LENGTH_s, 0.0);
        if (disappearTimer <= 0) reappear();
    }
}
