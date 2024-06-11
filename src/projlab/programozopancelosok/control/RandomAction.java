package projlab.programozopancelosok.control;


import java.util.function.Function;

/**
 * RandomAction class represents an action that can be randomly triggered.
 */
public class RandomAction {

    /**
     * The name of the random action.
     */
    private String name;

    /**
     * The chance of the action being triggered.
     */
    private final double chance;

    /**
     * The action to be executed when triggered.
     */
    private final Runnable action;

    /**
     * Constructs a RandomAction with the specified name, chance, and action.
     *
     * @param name   The name of the random action.
     * @param chance The chance of the action being triggered.
     * @param action The action to be executed when triggered.
     */
    public RandomAction(String name, double chance, Runnable action) {
        this.name = name;
        this.chance = chance;
        this.action = action;
    }

    /**
     * Executes the action if the randomly generated value is less than the chance.
     *
     * @param randVal The randomly generated value.
     */
    public void run(double randVal) {
        if (randVal < chance)
            action.run();
    }
}

