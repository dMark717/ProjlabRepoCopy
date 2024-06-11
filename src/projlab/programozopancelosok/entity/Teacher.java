package projlab.programozopancelosok.entity;

import projlab.programozopancelosok.item.Item;
import projlab.programozopancelosok.room.Door;
import projlab.programozopancelosok.room.Room;
import projlab.programozopancelosok.util.Logger;

/**
 * The Teacher class represents a teacher character in the game.
 * Teachers can move between rooms, interact with doors, meet other characters,
 * and handle encounters with students or other teachers.
 */
public class Teacher extends Person {
    public Teacher(String neptun) {
        super(neptun);
    }

    /**
     * Allows the teacher to choose a door to enter.
     * If successful, prints a success message; otherwise, prints a failure message.
     *
     * @param door The door to choose for entering.
     */
    public boolean chooseDoor(Door door) {
        Logger.CALLSTACK_PRINTLN("Teacher.chooseDoor(door)");
        if (door.enter(this)) {
            Logger.CALLSTACK_PRINTLN("Teacher entered successfully.");
            return true;
        } else {
            Logger.CALLSTACK_PRINTLN("Teacher could not enter.");
            return false;
        }
    }

    /**
     * Moves the teacher to the specified room and updates the room accordingly.
     *
     * @param room The room to visit.
     */
    public void visit(Room room) {
        Logger.CALLSTACK_PRINTLN("Teacher.visit() called.");
        if (currentRoom != null)
            currentRoom.removePerson(this);
        currentRoom = room;
        room.acceptTeacher(this);
    }

    /**
     * Initiates a meeting with another person.
     *
     * @param person The person to meet.
     */
    public void meet(Person person) {
        Logger.CALLSTACK_PRINTLN("Teacher.meet(person) called");
        person.acceptTeacher(this);
    }

    /**
     * Handles the encounter between the teacher and a student.
     * Checks for any defensive items in the student's inventory
     * and whether the student has invincibility time.
     * If not, the student dies.
     *
     * @param student The student encountered by the teacher.
     */
    public void acceptStudent(Student student) {
        Logger.CALLSTACK_PRINTLN("Teacher.acceptStudent(student) called");
        //Check if the student has InvincibilityTime
        if (student.isInvincible())
            return;

        for (Item item : student.inventory) {
            if (item.blockDeath())
                return;
        }

        //If the student has no TVSZ and no InvincibilityTime, the student dies
        student.die();
    }

    /**
     * Handles the encounter between the teacher and another teacher.
     * No action is taken, as nothing happens when a teacher meets another teacher.
     *
     * @param teacher The teacher encountered by the teacher.
     */
    public void acceptTeacher(Teacher teacher) {
    }

    /**
     * Accepts a cleaner for interaction.
     *
     * @param cleaner The cleaner to accept.
     */
    public void acceptCleaner(Cleaner cleaner) {
        Door dest = null;
        for (Door door : currentRoom.getDoorsInRoom()) {
            if (door.canEnterFrom(currentRoom))
                dest = door;
        }
        if (dest != null) {
            dest.enter(this);
        } else {
            //Print error message on the console
            System.out.println("No door to enter from the room");
        }
    }

    /**
     * Prints information about the teacher.
     *
     * @param pInventory Indicates whether to print inventory information.
     */
    @Override
    public void printInfo(boolean pInventory) {
        System.out.println("Teacher ID: " + neptun);
        if (pInventory) {
            for (Item it : inventory) {
                it.printInfo();
            }
        }
        System.out.println();
    }
}
