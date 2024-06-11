package projlab.programozopancelosok.entity;

import projlab.programozopancelosok.item.Item;
import projlab.programozopancelosok.room.Door;
import projlab.programozopancelosok.room.Room;
import projlab.programozopancelosok.util.Logger;

/**
 * Represents a student entity in the game.
 */
public class Student extends Person {

    /**
     * Constructs a Student object and initializes its current room.
     */
    public Student(String neptun) {
        super(neptun);
    }

    /**
     * Chooses a door to enter.
     *
     * @param d The door to enter.
     */
    public boolean chooseDoor(Door d) {
        Logger.CALLSTACK_PRINTLN("Student.chooseDoor(door) called");
        if (d.enter(this)) {
            Logger.CALLSTACK_PRINTLN("Student entered successfully.");
            return true;
        } else {
            Logger.CALLSTACK_PRINTLN("Student could not enter.");
            return false;
        }

    }

    /**
     * Visits a room.
     *
     * @param room The room to visit.
     */
    public void visit(Room room) {
        Logger.CALLSTACK_PRINTLN("Student.visit(room) called.");
        if (currentRoom != null) //xdd ez nem annyira elegáns de igy a legegyszerűbb kész xdd
            currentRoom.removePerson(this);
        currentRoom = room;
        room.acceptStudent(this);
    }

    /**
     * Meets another person.
     *
     * @param person The person to meet.
     */
    public void meet(Person person) {
        Logger.CALLSTACK_PRINTLN("Student.meet(person) called");
        person.acceptStudent(this);
    }

    /**
     * Accepts another student for interaction.
     *
     * @param student The student to accept.
     */
    public void acceptStudent(Student student) {
        // Nothing happens when a student meets another student
    }

    /**
     * Accepts a teacher for interaction.
     *
     * @param teacher The teacher to accept.
     */
    public void acceptTeacher(Teacher teacher) {
        Logger.CALLSTACK_PRINTLN("Student.acceptTeacher(teacher) called");

        // Check if the student has invincibility time
        if (this.isInvincible()) {
            return;
        }

        // Check if any item in inventory blocks death
        for (Item item : inventory) {
            if (item.blockDeath())
                return;
        }

        //check if the teacher is stunned
        if (teacher.stunTime > 0) return;
        // If the student has no item blocking death and no invincibility time, the student dies
        die();
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
     * Prints information about the student.
     *
     * @param pInventory Indicates whether to print inventory information.
     */
    @Override
    public void printInfo(boolean pInventory) {
        System.out.println("Student ID: " + neptun);
        int i = 0;
        if (pInventory) {
            for (Item it : inventory) {
                System.out.print("\t" + i + ".: ");
                it.printInfo();
                i++;
            }
        }
        System.out.println();
    }

    /**
     * Kills the student.
     */
    public void die() {
        Logger.CALLSTACK_PRINTLN("Student.die() called");
        isAlive = false;
    }
}
