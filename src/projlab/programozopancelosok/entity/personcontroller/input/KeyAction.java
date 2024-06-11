package projlab.programozopancelosok.entity.personcontroller.input;


import projlab.programozopancelosok.graphics.DisplayUpdateType;
import projlab.programozopancelosok.util.Flagger;

import java.util.Set;

import static projlab.programozopancelosok.graphics.DisplayUpdateType.*;

public enum KeyAction {

    //ROOM_ITEM
    /**
     * The action of decreasing the index of the selected item in the room.
     */
    DECR_SELECTED_ROOM_ITEM(ROOM_ITEM_UPDATE),
    /**
     * The action of increasing the index of the selected item in the room.
     */
    INCR_SELECTED_ROOM_ITEM(ROOM_ITEM_UPDATE),
    /**
     * The action of picking up the selected item from the floor.
     */
    PICKUP_SELECTED_ROOM_ITEM(ROOM_ITEM_UPDATE, INVENTORY_UPDATE),

    //INVENTORY
    DECR_SELECTED_INV_ITEM(INVENTORY_UPDATE),
    INCR_SELECTED_INV_ITEM(INVENTORY_UPDATE),
    /**
     * The action of using up the selected item in the inventory.
     */
    USE_SELECTED_INV_ITEM(FULL_UPDATE),
    DROP_SELECTED_INV_ITEM(INVENTORY_UPDATE, ROOM_ITEM_UPDATE),


    DECR_SELECTED_DOOR(DOORS_UPDATE),
    INCR_SELECTED_DOOR(DOORS_UPDATE),
    /**
     * The action of moving through the selected door.
     */
    MOVE_TROUGH_SELECTED_DOOR(FULL_UPDATE);

    KeyAction(DisplayUpdateType... updates) {
        displayUpdates = Set.of(updates);
    }

    private final Set<DisplayUpdateType> displayUpdates;

    public void setDisplayUpdateFlags(Flagger<DisplayUpdateType> displayUpdateFlagger) {
        for (DisplayUpdateType update : displayUpdates) {
            displayUpdateFlagger.setFlag(update);
        }
    }
}
