package projlab.programozopancelosok.room.strategy;

import projlab.programozopancelosok.control.Control;
import projlab.programozopancelosok.entity.Cleaner;
import projlab.programozopancelosok.entity.Student;
import projlab.programozopancelosok.entity.Teacher;
import projlab.programozopancelosok.util.Logger;

/**
 * Represents a Camembert strategy in the game.
 * This strategy poisons teachers if they are not wearing a mask.
 */
public class CamembertStrategy implements RoomStrategy {

    /**
     * The remaining time for the Camembert strategy.
     */
    double timer;

    /**
     * Constructs a CamembertStrategy object with a specified duration.
     *
     * @param second The duration of the strategy.
     */
    public CamembertStrategy(double second) {
        timer = second;
    }

    /**
     * Updates the strategy's state on each tick of the game.
     */
    @Override
    public void onTick() {
        timer -= Control.TICK_LENGTH_s;
    }

    @Override
    public void acceptAirFreshener() {

    }

    /**
     * Executes the strategy for students (has no effect).
     *
     * @param student The student affected by the strategy.
     */
    @Override
    public void executeStudentStrategy(Student student) {
        Logger.CALLSTACK_PRINTLN("CamembertStrategy.executeStudentStrategy() called.");
    }

    /**
     * Executes the strategy for teachers by poisoning them if they are not wearing a mask.
     *
     * @param teacher The teacher affected by the strategy.
     */
    @Override
    public void executeTeacherStrategy(Teacher teacher) {
        Logger.CALLSTACK_PRINTLN("CamembertStrategy.executeTeacherStrategy() called.");
        if (!teacher.isMaskOn()) {
            teacher.faint(10);
        }
    }

    /**
     * Executes the strategy for cleaners.
     *
     * @param cleaner The cleaner affected by the strategy.
     */
    public void executeCleanerStrategy(Cleaner cleaner) {

    }

    /**
     * Checks if the Camembert strategy is expired.
     *
     * @return True if the timer has expired, false otherwise.
     */
    @Override
    public boolean isExpired() {
        return timer <= 0.0;
    }

    @Override
    public boolean doesMakeRoomPoisonous() {
        return false;
    }

    public void printInfo() {

    }
}
