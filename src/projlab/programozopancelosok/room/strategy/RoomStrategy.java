package projlab.programozopancelosok.room.strategy;

import projlab.programozopancelosok.entity.Cleaner;
import projlab.programozopancelosok.entity.Student;
import projlab.programozopancelosok.entity.Teacher;

/**
 * Represents a room strategy interface in the game.
 * Different strategies (effects) are applied to students and teachers due to their own attributes.
 * Executed on each tick of the game.
 */
public interface RoomStrategy {

    /**
     * Updates the strategy's state on each tick of the game.
     */
    void onTick();

    void acceptAirFreshener();

    /**
     * Executes the strategy for students.
     *
     * @param student The student affected by the strategy.
     */
    void executeStudentStrategy(Student student);

    /**
     * Executes the strategy for teachers.
     *
     * @param teacher The teacher affected by the strategy.
     */
    void executeTeacherStrategy(Teacher teacher);

    /**
     * Executes the strategy for cleaners.
     *
     * @param cleaner The cleaner affected by the strategy.
     */
    void executeCleanerStrategy(Cleaner cleaner);

    /**
     * Checks if the strategy is expired.
     *
     * @return True if the strategy is expired, false otherwise.
     */
    boolean isExpired();

    boolean doesMakeRoomPoisonous();

    void printInfo();
}
