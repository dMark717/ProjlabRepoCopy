package projlab.programozopancelosok.entity.personcontroller;

import projlab.programozopancelosok.control.GameData;
import projlab.programozopancelosok.entity.Person;

/**
 * DummyPersonController class represents a dummy controller for a person in the game.
 * Its sole function is to do nothing and keep the person stationary.
 */
public class DummyPersonController extends PersonController {

    /**
     * Constructs a DummyPersonController with the specified person and game data.
     *
     * @param person   The person controlled by the dummy controller.
     * @param gameData The game data.
     */
    public DummyPersonController(Person person, GameData gameData) {
        super(person, gameData);
    }

    /**
     * Does nothing on each tick, keeping the person stationary.
     */
    @Override
    public void onTick() {
        super.onTick();
        // No action performed
    }
}
