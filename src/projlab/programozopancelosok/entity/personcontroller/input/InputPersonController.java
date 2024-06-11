package projlab.programozopancelosok.entity.personcontroller.input;

import projlab.programozopancelosok.control.GameData;
import projlab.programozopancelosok.entity.Person;
import projlab.programozopancelosok.entity.Student;
import projlab.programozopancelosok.entity.personcontroller.PersonController;
import projlab.programozopancelosok.graphics.DisplayUpdateType;
import projlab.programozopancelosok.graphics.Screen;
import projlab.programozopancelosok.util.Flagger;
import projlab.programozopancelosok.util.Logger;
import projlab.programozopancelosok.util.Util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import static projlab.programozopancelosok.entity.personcontroller.input.KeyAction.*;

/**
 * InputPersonController class represents a controller for a person in the game that responds to user input.
 * It allows mapping characters to specific actions that the controlled person can perform.
 */
public class InputPersonController extends PersonController implements KeyListener {
    /**
     * Map of characters to corresponding actions.
     */
    Layout keyLayout;

    Flagger<KeyAction> executionFlagger;
    Map<KeyAction, Runnable> actionFuncs;

    int selectedRoomItemIndex;
    int selectedDoorIndex;

    int selectedInvItemIndex;
    Flagger<DisplayUpdateType> displayUpdateFlagger;


    /**
     * Constructs an InputPersonController with the specified person, identifier, and game data.
     *
     * @param person   The person controlled by the input controller.
     * @param id       The identifier for the input controller.
     * @param gameData The game data.
     */
    public InputPersonController(Person person, String id, GameData gameData, Layout layout) {
        super(person, gameData);
        this.keyLayout = layout;
        displayUpdateFlagger = new Flagger<>();
        executionFlagger = new Flagger<>();

        actionFuncs = new HashMap<>();
        setActionFuncs();
    }

    private void setActionFuncs() {
        //region FÖLDI ITEMEK
        actionFuncs.put(DECR_SELECTED_ROOM_ITEM, () -> {
            //If empty, do nothing
            if (person.getCurrentRoom().getItemCount() == 0) return;

            //lefele számlál
            selectedRoomItemIndex = Util.posMod(--selectedRoomItemIndex, person.getCurrentRoom().getItemCount());
        });

        actionFuncs.put(INCR_SELECTED_ROOM_ITEM, () -> {
            //If empty, do nothing
            if (person.getCurrentRoom().getItemCount() == 0) return;

            //felfelé számlál
            selectedRoomItemIndex = Util.posMod(++selectedRoomItemIndex, person.getCurrentRoom().getItemCount());
        });
        actionFuncs.put(PICKUP_SELECTED_ROOM_ITEM, () -> {
            //If empty, do nothing
            if (person.getCurrentRoom().getItemCount() == 0) return;

            validateSelection();
            pickupItem(selectedRoomItemIndex);
            validateSelection();
        });
        //endregion

        //region INVENTORY
        actionFuncs.put(DECR_SELECTED_INV_ITEM, () -> {
            //If empty, do nothing
            if (person.getItemCount() == 0) return;

            //lefele számlál
            selectedInvItemIndex = Util.posMod(--selectedInvItemIndex, person.getItemCount());
            person.setSelectedItemIndex(selectedInvItemIndex);
        });
        actionFuncs.put(INCR_SELECTED_INV_ITEM, () -> {
            //If empty, do nothing
            if (person.getItemCount() == 0) return;

            //felfelé számlál
            selectedInvItemIndex = Util.posMod(++selectedInvItemIndex, person.getItemCount());
            person.setSelectedItemIndex(selectedInvItemIndex);
        });
        actionFuncs.put(USE_SELECTED_INV_ITEM, () -> {
            //If empty, do nothing
            if (person.getItemCount() == 0) return;

            validateSelection();
            useSelectedItem();
            validateSelection();
        });

        actionFuncs.put(DROP_SELECTED_INV_ITEM, () -> {
            //If empty, do nothing
            if (person.getItemCount() == 0) return;

            validateSelection();
            dropSelectedItem();
            validateSelection();
        });
        //endregion

        //region AJTÓK
        actionFuncs.put(DECR_SELECTED_DOOR, () -> {
            //If empty, do nothing
            if (person.getCurrentRoom().getDoorCount() == 0) return;
            selectedDoorIndex = Util.posMod(--selectedDoorIndex, person.getCurrentRoom().getDoorCount());
        });
        actionFuncs.put(INCR_SELECTED_DOOR, () -> {
            //If empty, do nothing
            if (person.getCurrentRoom().getDoorCount() == 0) return;
            //lefele számlál
            selectedDoorIndex = Util.posMod(++selectedDoorIndex, person.getCurrentRoom().getDoorCount());
        });
        actionFuncs.put(MOVE_TROUGH_SELECTED_DOOR, () -> {
            //If empty, do nothing
            if (person.getCurrentRoom().getDoorCount() == 0) return;

            validateSelection();
            moveTo(selectedDoorIndex);
            validateSelection();
        });
        //endregion
    }

    public int getSelectedRoomItemIndex() {
        selectedRoomItemIndex = Util.posMod(selectedRoomItemIndex, person.getCurrentRoom().getItemCount());
        return selectedRoomItemIndex;
    }

    public int getSelectedDoorIndex() {
        selectedDoorIndex = Util.posMod(selectedDoorIndex, person.getCurrentRoom().getDoorCount());
        return selectedDoorIndex;
    }

    public int getSelectedInvItemIndex() {
        selectedInvItemIndex = Util.posMod(selectedInvItemIndex, person.getItemCount());
        return selectedInvItemIndex;
    }

    private void validateSelection() {
        selectedInvItemIndex = Util.posMod(selectedInvItemIndex, person.getItemCount());
        person.setSelectedItemIndex(selectedInvItemIndex);
        selectedDoorIndex = Util.posMod(selectedDoorIndex, person.getCurrentRoom().getDoorCount());
        selectedRoomItemIndex = Util.posMod(selectedRoomItemIndex, person.getCurrentRoom().getItemCount());
    }

    public void subscribeToEvent(Screen screen) {
        screen.addKeyListener(this);
    }

    public static InputPersonController CreatePlayerAndCtrl(String id, GameData gameData, Layout layout) {
        Student player = new Student(id);
        return new InputPersonController(player, id, gameData, layout);
    }


    @Override
    public void onTick() {
        for (KeyAction action : KeyAction.values()) {
            if (!executionFlagger.isFlagSet(action)) continue;
            actionFuncs.get(action).run();
        }
        validateSelection();
        executionFlagger.resetFlags();
        person.onTick();
    }

    //region KeyListener

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Processes the key input and performs the corresponding action, if mapped.
     *
     * @param e The input to be processed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();

        if (!keyLayout.isValidKey(keyChar)) return;

        KeyAction action = keyLayout.getKeyAction(keyChar);

        //Notify the graphic flagger!
        action.setDisplayUpdateFlags(displayUpdateFlagger);

        //Flag the action to be executed
        executionFlagger.setFlag(action);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    //endregion
}

