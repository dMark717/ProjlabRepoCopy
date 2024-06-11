package projlab.programozopancelosok.room.strategy;

import projlab.programozopancelosok.entity.Cleaner;
import projlab.programozopancelosok.entity.Student;
import projlab.programozopancelosok.entity.Teacher;
import projlab.programozopancelosok.util.Logger;

/**
 * Represents a poison strategy in the game.
 * This strategy poisons everyone who is not wearing a mask.
 */
public class PoisonStrategy implements RoomStrategy {
    boolean expired = false;

    /** Constructs a PoisonStrategy object. */
    public PoisonStrategy() {}

    /**
     * Executes the strategy for students by poisoning them if they are not wearing a mask.
     * @param student The student affected by the strategy.
     */
    @Override
    public void executeStudentStrategy(Student student) {
        Logger.CALLSTACK_PRINTLN("PoisonStrategy.executeStudentStrategy() called.");
        if (!student.isMaskOn()) {
            student.faint(10);
        }
    }

    /**
     * Executes the strategy for teachers by poisoning them if they are not wearing a mask.
     * @param teacher The teacher affected by the strategy.
     */
    @Override
    public void executeTeacherStrategy(Teacher teacher) {
        Logger.CALLSTACK_PRINTLN("PoisonStrategy.executeTeacherStrategy() called.");
        if (!teacher.isMaskOn()) {
            teacher.faint(10);
        } else {
            //teacher.startMaskUsage();
        }
    }
    /**
     * Executes the strategy for cleaners.
     * @param cleaner The cleaner affected by the strategy.
     */
    public void executeCleanerStrategy(Cleaner cleaner){
        this.expired = true;
    }

    /**
     * Updates the strategy's state on each tick of the game.
     */
    @Override
    public void onTick() { }

    @Override
    public void acceptAirFreshener() {
        this.expired = true;
    }

    /**
     * Indicates that the PoisonStrategy is never expired.
     * @return Always returns false.
     */
    @Override
    public boolean isExpired() {
        return this.expired;
    }

    @Override
    public boolean doesMakeRoomPoisonous() {
        return true;
    }

    public void printInfo(){
        System.out.print("poison");
    }
}

