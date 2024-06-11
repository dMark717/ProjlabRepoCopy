package projlab.programozopancelosok.entity.personcontroller;

import projlab.programozopancelosok.control.GameData;
import projlab.programozopancelosok.entity.Person;
import projlab.programozopancelosok.room.Door;
import projlab.programozopancelosok.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * BasicAIController class represents a basic AI controller for a person in the game.
 */
public class BasicAIController extends PersonController {

    /**
     * The minimum wait time in ticks before making a move.
     */
    int minWaitTick;

    /**
     * The maximum wait time in ticks before making a move.
     */
    int maxWaitTick;

    /**
     * The time to wait before making a move.
     */
    int waitTime;

    /**
     * The previous door the person entered.
     */
    Door previousDoor;

    /**
     * Constructs a BasicAIController with the specified person, game data, minimum wait time, and maximum wait time.
     *
     * @param person      The person controlled by the AI.
     * @param gameData    The game data.
     * @param minWaitTick The minimum wait time in ticks before making a move.
     * @param maxWaitTick The maximum wait time in ticks before making a move.
     */
    public BasicAIController(Person person, GameData gameData, int minWaitTick, int maxWaitTick) {
        super(person, gameData);
        this.minWaitTick = minWaitTick;
        this.maxWaitTick = maxWaitTick;
        waitTime = gameData.getRandom().nextInt(minWaitTick, maxWaitTick);
    }

    /**
     * Executes the basic AI behavior for the person on each tick.
     */
    @Override
    public void onTick() {
        super.onTick();
        if (waitTime-- > 0) return;

        Random random = gameData.getRandom();

        Room currRoom = person.getCurrentRoom();
        List<Door> traversableDoors = new ArrayList<>();
        for (Door door : currRoom.getAllDoor()) {
            if (door.canEnterFrom(currRoom) && !door.equals(previousDoor))
                traversableDoors.add(door);
        }
        Door next = null;
        // If there is at least one door to traverse, randomly select one (excluding the previous door)
        if (traversableDoors.size() > 0) {
            int idx = random.nextInt(traversableDoors.size());
            next = traversableDoors.get(idx);
        } else if (previousDoor != null && previousDoor.canEnterFrom(currRoom)) {
            next = previousDoor;
        }

        if (next != null) {
            //Get door index
            int doorInd = currRoom.getDoorsInRoom().indexOf(next);
            this.moveTo(doorInd);
        }

        previousDoor = next;
        waitTime = random.nextInt(minWaitTick, maxWaitTick);
    }
}

