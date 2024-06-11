package projlab.programozopancelosok.entity;

import projlab.programozopancelosok.control.GameData;
import projlab.programozopancelosok.entity.personcontroller.PersonController;
import projlab.programozopancelosok.room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the controllers for each person in the game.
 */
public class PersonCtrlManager implements java.io.Serializable {
    GameData gameData;
    Map<String, PersonController> personControllers;

    /**
     * Initializes a new instance of the PersonCtrlManager class.
     *
     * @param gameData The game data associated with the manager.
     */
    public PersonCtrlManager(GameData gameData) {
        this.gameData = gameData;
        personControllers = new HashMap<>();
    }

    /**
     * Adds a person controller to the manager.
     *
     * @param pCtrl The person controller to add.
     */
    public void addPersonCtrl(PersonController pCtrl) {
        personControllers.put(pCtrl.getId(), pCtrl);
    }

    /**
     * Executes actions for each person controller on each tick.
     * Checks if each person is alive and manages the cemetery.
     */
    public void onTick() {
        for (PersonController personCtrl : personControllers.values()) {
            personCtrl.onTick();
        }
    }

    /**
     * Retrieves a person controller by ID.
     *
     * @param id The ID of the person controller to retrieve.
     * @return The person controller with the specified ID, or null if not found.
     */
    public PersonController getPersonCtrl(String id) {
        return personControllers.get(id);
    }

    /**
     * Checks if a Neptun ID is valid.
     *
     * @param neptun The Neptun ID to check.
     * @return True if the Neptun ID is valid, false otherwise.
     */
    public boolean isValidNeptun(String neptun) {
        return personControllers.containsKey(neptun);
    }

    /**
     * Retrieves all person controllers managed by this manager.
     *
     * @return A map of person controllers, where the keys are Neptun IDs and the values are the corresponding controllers.
     */
    public Map<String, PersonController> getPersonControllers() {
        return personControllers;
    }
}