package projlab.programozopancelosok.entity;

import projlab.programozopancelosok.item.Item;
import projlab.programozopancelosok.room.Door;
import projlab.programozopancelosok.room.Room;

/**
 * The Cleaner class represents a special type of person who is responsible for cleaning in the game.
 */
public class Cleaner extends Person {

    /**
     * Constructor for the Cleaner class.
     *
     * @param neptun The Neptun code of the Cleaner.
     */
    public Cleaner(String neptun) {
        super(neptun);
    }

    /**
     * Handles the entry into the chosen door by the cleaner.
     *
     * @param d The Door object through which the cleaner enters.
     * @return True if the entry is successful, otherwise false.
     */
    @Override
    public boolean chooseDoor(Door d) {
        return d.enter(this);
    }

    /**
     * Handles the entry into the chosen room by the cleaner.
     *
     * @param room The Room object into which the cleaner enters.
     */
    @Override
    public void visit(Room room) {
        if (currentRoom != null)
            currentRoom.removePerson(this);
        currentRoom = room;
        room.acceptCleaner(this);
    }

    /**
     * Handles the behavior when the cleaner meets another person.
     *
     * @param person The Person object the cleaner meets.
     */
    @Override
    public void meet(Person person) {
        person.acceptCleaner(this);
    }

    /**
     * Accepts the cleaning performed by a Student.
     *
     * @param student The Student object who is cleaning.
     */
    @Override
    void acceptStudent(Student student) {
        /*
        Cleaning, but in the specification it happens when entering,
         so it only needs to be implemented in the acceptCleaner method of Student and Teacher.
        */
    }

    /**
     * Accepts the cleaning performed by a Teacher.
     *
     * @param teacher The Teacher object who is cleaning.
     */
    @Override
    void acceptTeacher(Teacher teacher) {
        /*
         Cleaning, but in the specification it happens when entering,
         so it only needs to be implemented in the acceptCleaner method of Student and Teacher.
        */
    }

    /**
     * Handles the acceptance of cleaning performed by another Cleaner.
     *
     * @param cleaner The other Cleaner with whom the meeting occurs.
     */
    @Override
    void acceptCleaner(Cleaner cleaner) {
    }

    /**
     * Prints the information of the cleaner.
     *
     * @param pInventory True if the cleaner's items should be printed, otherwise false.
     */
    @Override
    public void printInfo(boolean pInventory) {
        System.out.println("Cleaner ID: " + neptun);
        if (pInventory) {
            for (Item it : inventory) {
                it.printInfo();
            }
        }
        System.out.println();
    }
}