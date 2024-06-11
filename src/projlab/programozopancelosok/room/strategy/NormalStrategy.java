package projlab.programozopancelosok.room.strategy;

import projlab.programozopancelosok.entity.Cleaner;
import projlab.programozopancelosok.entity.Student;
import projlab.programozopancelosok.entity.Teacher;
import projlab.programozopancelosok.util.Logger;

/**
 * Represents a normal strategy in the game.
 * This strategy has no effect on any person.
 */
public class NormalStrategy implements RoomStrategy {

    /** Constructs a NormalStrategy object. */
    public NormalStrategy() {}

    /**
     * Executes the strategy for students (has no effect).
     * @param student The student affected by the strategy.
     */
    @Override
    public void executeStudentStrategy(Student student) {
        Logger.CALLSTACK_PRINTLN("NormalStrategy.executeStudentStrategy() called.");
    }

    /**
     * Executes the strategy for teachers (has no effect).
     * @param teacher The teacher affected by the strategy.
     */
    @Override
    public void executeTeacherStrategy(Teacher teacher) {
        Logger.CALLSTACK_PRINTLN("NormalStrategy.executeTeacherStrategy() called.");
    }

    /**
     * Executes the strategy for cleaners.
     * @param cleaner The cleaner affected by the strategy.
     */
    public void executeCleanerStrategy(Cleaner cleaner){
        //nincs hatasa
    }

    /**
     * Updates the strategy's state on each tick of the game.
     */
    @Override
    public void onTick() { }

    @Override
    public void acceptAirFreshener() {

    }

    /**
     * Indicates that the NormalStrategy is never expired.
     * @return Always returns false.
     */
    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public boolean doesMakeRoomPoisonous() {
        return false;
    }

    public void printInfo(){
        System.out.print("normal");
    }
}

