package projlab.programozopancelosok.room.strategy;

import projlab.programozopancelosok.control.Control;
import projlab.programozopancelosok.entity.Cleaner;
import projlab.programozopancelosok.entity.Student;
import projlab.programozopancelosok.entity.Teacher;
import projlab.programozopancelosok.util.Logger;

/**
 * Represents a board cleaner strategy in the game.
 * This strategy affects teachers by stunning them until the board cleaner dries out.
 */
public class BoardCleanerStrategy implements RoomStrategy {

    /**
     * The remaining time for the board cleaner to dry out.
     */
    double timer;

    /**
     * Constructs a BoardCleanerStrategy object.
     */
    public BoardCleanerStrategy(double time) {
        timer = time;
    }

    /**
     * Executes the strategy for students (has no effect).
     *
     * @param student The student affected by the strategy.
     */
    @Override
    public void executeStudentStrategy(Student student) {
        Logger.CALLSTACK_PRINTLN("BoardCleanerStrategy.executeStudentStrategy() called.");
    }

    /**
     * Executes the strategy for teachers by stunning them.
     *
     * @param teacher The teacher affected by the strategy.
     */
    @Override
    public void executeTeacherStrategy(Teacher teacher) {
        Logger.CALLSTACK_PRINTLN("BoardCleanerStrategy.executeTeacherStrategy() called.");
        teacher.stun(timer);
    }

    /**
     * Executes the strategy for cleaners.
     *
     * @param cleaner The cleaner affected by the strategy.
     */
    public void executeCleanerStrategy(Cleaner cleaner) {
        //van hatasa?
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
     * Checks if the board cleaner strategy is expired.
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
        System.out.print("boardCleaner");
    }
}
