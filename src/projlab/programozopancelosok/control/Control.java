package projlab.programozopancelosok.control;

import projlab.programozopancelosok.entity.PersonCtrlManager;
import projlab.programozopancelosok.graphics.DisplayUpdateType;
import projlab.programozopancelosok.room.Room;
import projlab.programozopancelosok.room.RoomManager;
import projlab.programozopancelosok.util.Flagger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Control class manages the game control logic.
 */
public class Control {
    Flagger<DisplayUpdateType> displayUpdater;

    /**
     * Number of ticks per second.
     */
    public static final double TICKS_PER_SEC = 20;

    /**
     * Length of each tick in seconds.
     */
    public static final double TICK_LENGTH_s = 1 / TICKS_PER_SEC;

    /**
     * Length of each tick in milliseconds.
     */
    public static final int TICK_LENGTH_ms = (int) (TICK_LENGTH_s * 1000);

    /**
     * Game data instance.
     */
    GameData gameData;

    /**
     * List of random actions.
     */
    List<RandomAction> randomActions;

    /**
     * Current game state.
     */
    GameState gameState;
    RoomManager roomManager;
    PersonCtrlManager personCtrlManager;

    /**
     * Total time elapsed in the game.
     */
    double timeElapsed;

    /**
     * Total ticks elapsed in the game.
     */
    int ticksElapsed;

    /**
     * Ticks per second.
     */
    int ticksPerSecond;

    /**
     * Constructs a Control object with a specified random instance.
     *
     * @param random The random instance to use.
     */
    public Control(Random random) {
        this(new GameData(random));
    }

    /**
     * Constructs a Control object with a specified game data instance.
     *
     * @param gameData The game data instance.
     */
    public Control(GameData gameData) {
        this.gameData = gameData;
        displayUpdater = new Flagger<>();
        gameState = GameState.RUNNING;
        randomActions = new ArrayList<>();

        roomManager = gameData.getRoomManager();
        personCtrlManager = gameData.getPersonCtrlManager();

    }

    /**
     * Initializes random actions for the game.
     */
    public void initRandomActions(double mult) {
        randomActions.add(new RandomAction("DoorDisappear", 0.0002 * mult, () -> {
            if (!roomManager.getDoors().isEmpty()) {
                roomManager.getDoors().get(roomManager.getRandomDoor()).disappear();
                displayUpdater.setFlag(DisplayUpdateType.DOORS_UPDATE);
                System.out.println("\t Door disappeard");
            }

        }));
        //SZOBA OSZTÓDÁSA
        randomActions.add(new RandomAction("RoomSplit", 0.0002 * mult, () -> {
            roomManager.splitRoom(roomManager.getRandomRoomId());
            System.out.println("\tRoom splitted somewhere :D");
            displayUpdater.setFlag(DisplayUpdateType.FULL_UPDATE);
        }));
        //SZOBA MERGE
        randomActions.add(new RandomAction("RoomMerge", 0.0002 * mult, () -> {
            //Csak akkor merge, ha több, mint két szoba van
            if (roomManager.getRooms().size() > 2) {
                String room1ID = roomManager.getRandomRoomId();
                Room room = roomManager.getRoom(room1ID);
                //ha van szomszédja
                if (!room.getDoorsInRoom().isEmpty() && room.getDoor(0).getOpposite(room) != null) {
                    String room2ID = room.getDoor(0).getOpposite(room).getRoomCode();
                    roomManager.mergeRoomWith(room1ID, room2ID);
                    displayUpdater.setFlag(DisplayUpdateType.FULL_UPDATE);
                    System.out.println("\tMerged 2 rooms");
                }
            }
        }));
    }

    /**
     * Processes a tick in the game.
     */
    public void processTick() {
        roomManager.onTick();
        personCtrlManager.onTick();

        for (RandomAction action : randomActions) {
            action.run(gameData.random.nextDouble());
        }

        timeElapsed += TICK_LENGTH_s;
        ticksElapsed++;
    }

    /**
     * Gets the total ticks elapsed in the game.
     *
     * @return The total ticks elapsed.
     */
    public int getTicksElapsed() {
        return ticksElapsed;
    }

    /**
     * Gets the game data instance.
     *
     * @return The game data instance.
     */
    public GameData getGameData() {
        return gameData;
    }

    /**
     * Gets the room manager instance.
     *
     * @return The room manager instance.
     */
    public RoomManager getRoomManager() {
        return gameData.getRoomManager();
    }

    /**
     * Gets the person controller manager instance.
     *
     * @return The person controller manager instance.
     */
    public PersonCtrlManager getPersonCtrlManager() {
        return gameData.getPersonCtrlManager();
    }

    /**
     * Gets the current game state.
     *
     * @return The current game state.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Sets the current game state.
     *
     * @param state The game state to set.
     */
    public void setGameState(GameState state) {
        gameState = state;
    }

    /**
     * Skips a certain number of ticks in the game.
     *
     * @param ticks The number of ticks to skip.
     */
    public void skip(int ticks) {
    }
}