package projlab.programozopancelosok.graphics;

import projlab.programozopancelosok.entity.Person;
import projlab.programozopancelosok.room.Door;

import javax.swing.*;

public class AssetHandler {

    /**
     * The ImageIcon objects representing the title and the doors in the game.
     */
    public static final ImageIcon TITLE = new ImageIcon("./assets/title.png");
    public static final ImageIcon DOOR_TWO_WAY_ICON = new ImageIcon("./assets/doors/twoway.png");
    public static final ImageIcon DOOR_TWO_WAY_CLOSED_ICON = new ImageIcon("./assets/doors/twoway_closed.png");
    public static final ImageIcon DOOR_RIGHT_ICON = new ImageIcon("./assets/doors/right.png");
    public static final ImageIcon DOOR_RIGHT_CLOSED_ICON = new ImageIcon("./assets/doors/right_closed.png");
    public static final ImageIcon DOOR_LEFT_ICON = new ImageIcon("./assets/doors/left.png");
    public static final ImageIcon DOOR_LEFT_CLOSED_ICON = new ImageIcon("./assets/doors/left_closed.png");

    /**
     * Returns the ImageIcon object representing the door.
     *
     * @param door   The Door object to be represented.
     * @param player The Person object who is the player.
     * @return The ImageIcon object representing the door.
     */
    public static ImageIcon getDoorIcon(Door door, Person player) {

        //Ha ketiranyu
        if (door.isTwoWay()) {
            if (!door.isOpen())
                return DOOR_TWO_WAY_CLOSED_ICON;

            return DOOR_TWO_WAY_ICON;
        }

        //ha az adott szobabol be lehet lepni, akkor jobb

        if (door.canEnterFrom(player.getCurrentRoom())) {
            if (!door.isOpen())
                return DOOR_RIGHT_CLOSED_ICON;

            return DOOR_RIGHT_ICON;
        }

        //különben bal
        if (!door.isOpen()) {
            return DOOR_LEFT_CLOSED_ICON;
        }
        return DOOR_LEFT_ICON;
    }


}
