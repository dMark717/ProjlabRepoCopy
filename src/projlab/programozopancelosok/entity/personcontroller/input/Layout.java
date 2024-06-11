package projlab.programozopancelosok.entity.personcontroller.input;

import java.util.Map;


public class Layout {
    Map<Character, KeyAction> layout;

    private Layout(Map<Character, KeyAction> layout) {
        this.layout = layout;
    }

    public KeyAction getKeyAction(char key) {
        return layout.get(key);
    }

    public static final Layout P1_LAYOUT = new Layout(Map.of(
            '1', KeyAction.DECR_SELECTED_INV_ITEM,
            '2', KeyAction.INCR_SELECTED_INV_ITEM,
            '3', KeyAction.USE_SELECTED_INV_ITEM,
            '4', KeyAction.DROP_SELECTED_INV_ITEM,

            'q', KeyAction.DECR_SELECTED_DOOR,
            'w', KeyAction.INCR_SELECTED_DOOR,
            'e', KeyAction.MOVE_TROUGH_SELECTED_DOOR,

            'a', KeyAction.DECR_SELECTED_ROOM_ITEM,
            's', KeyAction.INCR_SELECTED_ROOM_ITEM,
            'd', KeyAction.PICKUP_SELECTED_ROOM_ITEM
    ));
    public static final Layout P2_LAYOUT = new Layout(Map.of(
            '6', KeyAction.DECR_SELECTED_INV_ITEM,
            '7', KeyAction.INCR_SELECTED_INV_ITEM,
            '8', KeyAction.USE_SELECTED_INV_ITEM,
            '9', KeyAction.DROP_SELECTED_INV_ITEM,

            'z', KeyAction.DECR_SELECTED_DOOR,
            'u', KeyAction.INCR_SELECTED_DOOR,
            'i', KeyAction.MOVE_TROUGH_SELECTED_DOOR,

            'h', KeyAction.DECR_SELECTED_ROOM_ITEM,
            'j', KeyAction.INCR_SELECTED_ROOM_ITEM,
            'k', KeyAction.PICKUP_SELECTED_ROOM_ITEM
    ));

    public boolean isValidKey(char keyChar) {
        return layout.containsKey(keyChar);
    }
}
