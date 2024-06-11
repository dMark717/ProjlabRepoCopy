package projlab.programozopancelosok.item;

import projlab.programozopancelosok.entity.Person;
import projlab.programozopancelosok.room.Room;
import projlab.programozopancelosok.room.strategy.BoardCleanerStrategy;

/**
 * Represents a board cleaner item in the game.
 */
public class BoardCleaner extends Item {


    public BoardCleaner(boolean validItem, boolean sticky) {
        super(validItem, sticky);
    }

    /**
     * Activates the board cleaner item, adding a board cleaner strategy to the current room.
     *
     * @param p The person using the item.
     */
    @Override
    public void onUse(Person p) {
        Room room = p.getCurrentRoom();

        room.addStrategy(new BoardCleanerStrategy(30.0));
        expire();
    }

    @Override
    public void printInfo() {
        System.out.print("BoardClr");
    }

    @Override
    public String toString() {
        return "BoardCleaner";
    }
}
