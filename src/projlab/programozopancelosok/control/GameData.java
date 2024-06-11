package projlab.programozopancelosok.control;

import projlab.programozopancelosok.entity.personcontroller.PersonController;
import projlab.programozopancelosok.entity.PersonCtrlManager;
import projlab.programozopancelosok.room.RoomManager;

import java.io.*;
import java.util.*;

/**
 * GameData class manages the game data, including the random instance, person controller manager, and room manager.
 */
public class GameData implements java.io.Serializable {


    /**
     * The random instance for generating random numbers. This field does not need to be serialized.
     */
    Random random;

    /**
     * The manager for handling person controllers in the game.
     */
    PersonCtrlManager personCtrlManager;

    /**
     * The manager for handling rooms in the game.
     */
    RoomManager roomManager;

    /**
     * Constructs a GameData object with the specified random instance.
     *
     * @param random The random instance to use.
     */
    public GameData(Random random) {
        this.random = random;
        personCtrlManager = new PersonCtrlManager(this);
        roomManager = new RoomManager(this);
    }

    /**
     * Gets the random instance used in the game.
     *
     * @return The random instance.
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Gets the room manager instance.
     *
     * @return The room manager instance.
     */
    public RoomManager getRoomManager() {
        return roomManager;
    }

    /**
     * Gets the person controller manager instance.
     *
     * @return The person controller manager instance.
     */
    public PersonCtrlManager getPersonCtrlManager() {
        return personCtrlManager;
    }

    /**
     * Adds a person controller to the game and places them in the specified room.
     *
     * @param pCtrl         The person controller to add.
     * @param roomToPlaceId The ID of the room where the person controller will be placed.
     */
    public void addPersonCtrl(PersonController pCtrl, String roomToPlaceId) {
        personCtrlManager.addPersonCtrl(pCtrl);
        roomManager.insertPerson(pCtrl.getPerson(), roomToPlaceId);
    }
}
